package com.br.montesan.restaurant.service;

import com.br.montesan.restaurant.dto.MenuItemDto;
import com.br.montesan.restaurant.exceptions.StatusChangeException;
import com.br.montesan.restaurant.repository.ProductRepository;
import com.br.montesan.restaurant.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final ProductRepository repository;

    public List<Product> listAll(){
        return repository.findAll();
    }

    public Product getProduct(Long prdId){
        return repository.findById(prdId).orElse(null);
    }

    public MenuItemDto changeStatus(Long prdId, Long status) {
        Product product = repository.findById(prdId).orElseThrow(() -> new StatusChangeException("This product does not exists."));

        if(isValid(product)){
            product.getStatus().setStatus(status);
            product = repository.save(product);
        }else{
            throw new StatusChangeException(getMessage(product));
        }

        return product.toDto();
    }

    protected String getMessage(Product product) {
        StringBuilder message = new StringBuilder();
        message.append("It is not possible change the status of Menu Item ")
                .append(product.getPrdName())
                .append(" because the status of its components are invalids.");
        return message.toString();
    }

    public boolean isValid(Product product) {
        return product.getType().isValid(product.getChildren());
    }

    public boolean isValid(Long prdId) {
        Product product = repository.findById(prdId).orElse(null);

        return product.getType().isValid(product.getChildren());
    }
}

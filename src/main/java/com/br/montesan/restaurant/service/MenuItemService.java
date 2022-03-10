package com.br.montesan.restaurant.service;

import com.br.montesan.restaurant.dto.MenuItemDto;
import com.br.montesan.restaurant.entity.Product;
import com.br.montesan.restaurant.exceptions.StatusChangeException;
import com.br.montesan.restaurant.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final ProductRepository repository;

    public List<MenuItemDto> listAll(){
        return repository.findAll().stream().map(pro -> pro.toDto()).collect(Collectors.toList());
    }

    public MenuItemDto getMenuItem(Long prdId){
        Product product = getProduct(prdId);
        return product.toDto();
    }

    public MenuItemDto changeStatus(Long prdId, Long status) {
        Product product = getProduct(prdId);

        if(isValid(product)){
            product.getStatus().setStatus(status);
            product = repository.save(product);
        }else{
            throw new StatusChangeException(getMessage(product));
        }

        return product.toDto();
    }

    private Product getProduct(Long prdId) {
        return repository.findById(prdId).orElseThrow(() -> new StatusChangeException("This Menu Item does not exists."));
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
}

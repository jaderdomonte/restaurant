package com.br.montesan.restaurant.controller;

import com.br.montesan.restaurant.dto.MenuItemDto;
import com.br.montesan.restaurant.entity.Product;
import com.br.montesan.restaurant.service.MenuItemService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu-item")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService service;

    @GetMapping
    public ResponseEntity<List<Product>> listProducts(){
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{prdId}")
    public ResponseEntity<Product> getProduct(@PathVariable("prdId") Long prdId){
        return ResponseEntity.ok(service.getProduct(prdId));
    }

    @GetMapping("/validate/{prdId}")
    public ResponseEntity<Boolean> isValid(@PathVariable("prdId") Long prdId){
        return ResponseEntity.ok(service.isValid(prdId));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status changed successfully."),
            @ApiResponse(code = 412, message = "Status could not be changed because something went wrong.")
    })
    @PutMapping(value = "/{prdId}/{status}", produces = "application/json")
    public ResponseEntity<MenuItemDto> changeStatus(@PathVariable("prdId") Long prdId,
                                          @PathVariable("status") Long status){
        MenuItemDto menuItem = service.changeStatus(prdId, status);
        return ResponseEntity.ok(menuItem);
    }
}

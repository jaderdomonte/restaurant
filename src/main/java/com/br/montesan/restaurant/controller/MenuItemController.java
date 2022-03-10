package com.br.montesan.restaurant.controller;

import com.br.montesan.restaurant.dto.MenuItemDto;
import com.br.montesan.restaurant.entity.Product;
import com.br.montesan.restaurant.service.MenuItemService;
import io.swagger.annotations.ApiOperation;
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

    @ApiResponse(code = 200, message = "List all Menu Items done.")
    @ApiOperation(value = "List all Menu Items.", response = MenuItemDto[].class, produces = "application/json")
    @GetMapping
    public ResponseEntity<List<MenuItemDto>> listProducts(){
        return ResponseEntity.ok(service.listAll());
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a Menu Item by its id done."),
            @ApiResponse(code = 412, message = "Menu Item does not exists.")
    })
    @ApiOperation(value = "Return a Menu Item by its id.", response = MenuItemDto.class, produces = "application/json")
    @GetMapping(value = "/{prdId}", produces = "application/json")
    public ResponseEntity<MenuItemDto> getProduct(@PathVariable("prdId") Long prdId){
        return ResponseEntity.ok(service.getMenuItem(prdId));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Status changed successfully."),
            @ApiResponse(code = 412, message = "Status could not be changed because something went wrong.")
    })
    @ApiOperation(value = "Change Status Menu Item.", response = MenuItemDto.class, produces = "application/json")
    @PutMapping(value = "/{prdId}/{status}", produces = "application/json")
    public ResponseEntity<MenuItemDto> changeStatus(@PathVariable("prdId") Long prdId,
                                          @PathVariable("status") Long status){
        MenuItemDto menuItem = service.changeStatus(prdId, status);
        return ResponseEntity.ok(menuItem);
    }
}

package com.br.montesan.restaurant.controller;

import com.br.montesan.restaurant.dto.MenuItemDto;
import com.br.montesan.restaurant.service.MenuItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class MenuItemControllerTest {

    @InjectMocks
    private MenuItemController menuItemController;

    @Mock
    private MenuItemService menuItemService;

    @Test
    void listProducts_When_existsMenuItems_Then_shouldReturnMenuItemsAndHttp200_Test(){
        doReturn(new ArrayList<>()).when(menuItemService).listAll();

        ResponseEntity<List<MenuItemDto>> response = menuItemController.listProducts();

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void getProduct_When_existsMenuItem_Then_shouldReturnMenuItemAndHttp200_Test(){
        doReturn(MenuItemDto.builder().build()).when(menuItemService).getMenuItem(anyLong());

        ResponseEntity<MenuItemDto> response = menuItemController.getProduct(1L);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void changeStatus_When_componentStatusIsValid_Then_shouldChangeStatusAndReturnHttp200_Test(){
        MenuItemDto menuItem = MenuItemDto.builder().build();

        doReturn(menuItem).when(menuItemService).changeStatus(anyLong(), anyLong());

        ResponseEntity<MenuItemDto> response = menuItemController.changeStatus(1L, 1L);

        assertEquals(200, response.getStatusCode().value());
    }
}
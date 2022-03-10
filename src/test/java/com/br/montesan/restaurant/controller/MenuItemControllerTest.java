package com.br.montesan.restaurant.controller;

import com.br.montesan.restaurant.dto.MenuItemDto;
import com.br.montesan.restaurant.service.MenuItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

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
    void changeStatus_When_componentStatusIsValid_Then_shouldChangeStatusAndReturnHttp200_Test(){
        MenuItemDto menuItem = MenuItemDto.builder().build();

        doReturn(menuItem).when(menuItemService).changeStatus(anyLong(), anyLong());

        ResponseEntity<MenuItemDto> response = menuItemController.changeStatus(1L, 1L);

        assertEquals(200, response.getStatusCode().value());
    }
}
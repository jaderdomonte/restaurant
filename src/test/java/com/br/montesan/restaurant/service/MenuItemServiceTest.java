package com.br.montesan.restaurant.service;

import com.br.montesan.restaurant.dto.MenuItemDto;
import com.br.montesan.restaurant.entity.Product;
import com.br.montesan.restaurant.entity.ProductComponent;
import com.br.montesan.restaurant.entity.ProductComponentId;
import com.br.montesan.restaurant.entity.ProductStatus;
import com.br.montesan.restaurant.enums.Type;
import com.br.montesan.restaurant.exceptions.StatusChangeException;
import com.br.montesan.restaurant.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest {

    @InjectMocks
    @Spy
    private MenuItemService menuItemService;

    @Mock
    private ProductRepository productRepository;


    @Test
    void listAll_test(){
        doReturn(new ArrayList<>()).when(productRepository).findAll();

        List<MenuItemDto> menuItemDtos = menuItemService.listAll();

        assertNotNull(menuItemDtos);
    }

    @Test
    void getMenuItem_test(){
        doReturn(Optional.of(Product.builder().build())).when(productRepository).findById(anyLong());

        MenuItemDto menuItem = menuItemService.getMenuItem(1L);

        assertNotNull(menuItem);
    }

    @Test
    void isValid_When_menuItemIsProduct_Then_returnTrue_test(){
        Product hamburguer = Product.builder().prdId(1L).type(Type.PRODUCT).prdName("HAMBURGER").build();

        boolean valid = menuItemService.isValid(hamburguer);

        assertTrue(valid);
    }

    @Test
    void isValid_When_menuItemIsChoiceAndStatusIsValid_Then_returnTrue_test(){
        Product drinkChoice = getValidChoice();

        boolean valid = menuItemService.isValid(drinkChoice);

        assertTrue(valid);
    }

    @Test
    void isValid_When_menuItemIsChoiceAndStatusIsInvalid_Then_returnFalse_test(){
        Product drinkChoice = getInvalidChoice();

        boolean valid = menuItemService.isValid(drinkChoice);

        assertFalse(valid);
    }

    @Test
    void isValid_When_menuItemIsValueMealAndStatusIsValid_Then_returnTrue_test(){
        Product validMeal = getValidValueMeal();

        boolean valid = menuItemService.isValid(validMeal);

        assertTrue(valid);
    }

    @Test
    void isValid_When_menuItemIsValueMealAndStatusIsInvalid_Then_returnFalse_test(){
        Product validMeal = getInvalidValueMeal();

        boolean valid = menuItemService.isValid(validMeal);

        assertFalse(valid);
    }

    @Test
    void getMessageTest(){
        Product hamburguer = Product.builder().prdId(1L).type(Type.PRODUCT).prdName("HAMBURGER").build();

        String message = menuItemService.getMessage(hamburguer);

        assertEquals("It is not possible change the status of Menu Item HAMBURGER because the status of its components are invalids.", message);
    }

    @Test
    void changeStatus_When_menuItemStatusIsValid_Then_changeStatus_test(){
        Product validMeal = getValidValueMeal();

        doReturn(Optional.of(validMeal)).when(productRepository).findById(anyLong());
        doReturn(true).when(menuItemService).isValid(any(Product.class));
        doReturn(validMeal).when(productRepository).save(any(Product.class));

        menuItemService.changeStatus(1L, 1L);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void changeStatus_When_menuItemStatusIsInvalid_Then_shouldThrowStatusChangeException_test(){
        StatusChangeException exception = Assertions.assertThrows(StatusChangeException.class, () -> {
            Product validMeal = getValidValueMeal();

            doReturn(Optional.of(validMeal)).when(productRepository).findById(anyLong());
            doReturn(false).when(menuItemService).isValid(any(Product.class));

            menuItemService.changeStatus(1L, 1L);
        });

        Assertions.assertEquals("It is not possible change the status of Menu Item BIG MAC MEAL " +
                "because the status of its components are invalids.", exception.getMessage());
    }

    private List<ProductComponent> getValidComponents(){
        ProductStatus active = ProductStatus.builder().prdId(2L).status(1L).build();
        ProductStatus inactive = ProductStatus.builder().prdId(3L).status(0L).build();
        Product coke = Product.builder().prdId(2L).type(Type.PRODUCT).prdName("COKE").status(active).build();
        Product sprite = Product.builder().prdId(3L).type(Type.PRODUCT).prdName("SPRITE").status(inactive).build();
        ProductComponentId cokeId = ProductComponentId.builder().prdId(6L).child(coke).build();
        ProductComponentId spriteId = ProductComponentId.builder().prdId(6L).child(sprite).build();
        ProductComponent cokeComponent = ProductComponent.builder().id(cokeId).build();
        ProductComponent spriteComponent = ProductComponent.builder().id(spriteId).build();
        return Arrays.asList(cokeComponent, spriteComponent);
    }

    private Product getValidChoice(){
        ProductStatus active = ProductStatus.builder().prdId(2L).status(1L).build();
        return Product.builder().prdId(6L).type(Type.CHOICE).
                prdName("DRINK CHOICE").status(active).
                children(getValidComponents()).build();
    }

    private List<ProductComponent> getInvalidComponents(){
        ProductStatus inactive = ProductStatus.builder().prdId(5L).status(0L).build();
        Product fries = Product.builder().prdId(2L).type(Type.PRODUCT).prdName("FRIES").status(inactive).build();
        Product nuggets = Product.builder().prdId(3L).type(Type.PRODUCT).prdName("NUGGETS").status(inactive).build();
        ProductComponentId friesId = ProductComponentId.builder().prdId(6L).child(fries).build();
        ProductComponentId nuggetsId = ProductComponentId.builder().prdId(6L).child(nuggets).build();
        ProductComponent friesComponent = ProductComponent.builder().id(friesId).build();
        ProductComponent nuggetsComponent = ProductComponent.builder().id(nuggetsId).build();
        return Arrays.asList(friesComponent, nuggetsComponent);
    }

    private Product getInvalidChoice(){
        ProductStatus inactive = ProductStatus.builder().prdId(5L).status(0L).build();
        return Product.builder().prdId(7L).type(Type.CHOICE).
                prdName("SIDE CHOICE").status(inactive).
                children(getInvalidComponents()).build();
    }

    private Product getValidValueMeal(){
        ProductStatus active = ProductStatus.builder().prdId(2L).status(1L).build();
        Product validChoice = getValidChoice();
        ProductComponentId validChoiceId = ProductComponentId.builder().prdId(8L).child(validChoice).build();
        ProductComponent drinkChoice = ProductComponent.builder().id(validChoiceId).build();

        return Product.builder().prdId(8L).type(Type.VALUE_MEAL).
                prdName("BIG MAC MEAL").status(active).
                children(Arrays.asList(drinkChoice)).build();
    }

    private Product getInvalidValueMeal(){
        ProductStatus active = ProductStatus.builder().prdId(2L).status(1L).build();
        Product invalidChoice = getInvalidChoice();
        ProductComponentId invalidChoiceId = ProductComponentId.builder().prdId(8L).child(invalidChoice).build();
        ProductComponent drinkChoice = ProductComponent.builder().id(invalidChoiceId).build();

        return Product.builder().prdId(8L).type(Type.VALUE_MEAL).
                prdName("BIG MAC MEAL").status(active).
                children(Arrays.asList(drinkChoice)).build();
    }
}
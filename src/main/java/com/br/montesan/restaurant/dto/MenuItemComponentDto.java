package com.br.montesan.restaurant.dto;

import com.br.montesan.restaurant.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemComponentDto {

    private Long prdId;
    private Product child;
}

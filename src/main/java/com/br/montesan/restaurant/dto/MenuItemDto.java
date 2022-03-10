package com.br.montesan.restaurant.dto;

import com.br.montesan.restaurant.entity.ProductComponent;
import com.br.montesan.restaurant.entity.ProductStatus;
import com.br.montesan.restaurant.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemDto {

    private Long prdId;

    private String prdName;

    private Type type;

    private ProductStatus status;

    private List<ProductComponent> children;
}

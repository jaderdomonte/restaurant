package com.br.montesan.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "PRODUCT_COMPONENTS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductComponent implements Serializable {

    @EmbeddedId
    private ProductComponentId id;
}

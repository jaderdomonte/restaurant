package com.br.montesan.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "PRODUCT_STATUS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStatus {

    @Id
    @Column(name = "PRD_ID")
    private Long prdId;

    @Column(name = "STATUS")
    private Long status;
}

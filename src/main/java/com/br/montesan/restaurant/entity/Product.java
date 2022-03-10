package com.br.montesan.restaurant.entity;

import com.br.montesan.restaurant.dto.MenuItemDto;
import com.br.montesan.restaurant.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {

    @Id
    @Column(name = "PRD_ID")
    private Long prdId;

    @Column(name = "PRD_NAME")
    private String prdName;

    @Column(name = "TYPE")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "PRD_ID", insertable = false, updatable = false)
    private ProductStatus status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "id.prdId")
    private List<ProductComponent> children;

    public boolean hasChildren(){
        return this.children != null && this.children.size() > 0;
    }

    public MenuItemDto toDto(){
        return new ModelMapper().map(this, MenuItemDto.class);
    }
}

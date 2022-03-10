package com.br.montesan.restaurant.enums;

import com.br.montesan.restaurant.entity.ProductComponent;

import java.util.List;
import java.util.stream.Collectors;

public enum Type {

    PRODUCT(1L){
        @Override
        public boolean isValid(List<ProductComponent> children){
            return true;
        }
    }, CHOICE(2L){
        @Override
        public boolean isValid(List<ProductComponent> children){
            return children.stream().anyMatch(child -> child.getId().getChild().getStatus().getStatus().equals(Status.ACTIVE.getId()));
        }
    }, VALUE_MEAL(3L){
        @Override
        public boolean isValid(List<ProductComponent> children){
            List<ProductComponent> components = children.stream().filter(child -> child.getId().getChild().hasChildren()).collect(Collectors.toList());
            return components.stream().allMatch(child -> child.getId().getChild().getStatus().getStatus().equals(Status.ACTIVE.getId()));
        }
    };

    private Long code;

    private Type(Long code){
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

    public abstract boolean isValid(List<ProductComponent> children);
}

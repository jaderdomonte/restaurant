package com.br.montesan.restaurant.enums;

public enum Status {

    INACTIVE(0L), ACTIVE(1L);

    private Long id;

    private Status(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

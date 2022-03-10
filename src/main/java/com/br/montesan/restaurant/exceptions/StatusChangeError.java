package com.br.montesan.restaurant.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusChangeError {

    protected String title;
    protected Integer status;
    protected String detail;
    protected Long date;
    protected String message;
}

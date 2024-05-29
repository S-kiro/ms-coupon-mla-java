package com.meli.mla.exception;

import com.meli.mla.exception.dto.ExceptionDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MsCouponMlaException extends Exception {

    private final ExceptionDTO exceptionDTO;

    public MsCouponMlaException(String message, ExceptionDTO exceptionDTO) {
        super(message);
        this.exceptionDTO = exceptionDTO;
    }

    public MsCouponMlaException(String message, ExceptionDTO exceptionDTO, Throwable e) {
        super(message, e);
        this.exceptionDTO = exceptionDTO;
    }
}

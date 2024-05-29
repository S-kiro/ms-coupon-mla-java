package com.meli.mla.exception.handler;

import com.meli.mla.exception.MsCouponMlaException;
import com.meli.mla.exception.dto.ExceptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({MsCouponMlaException.class})
    public ResponseEntity<ExceptionDTO> handlerMsCouponMlaException(MsCouponMlaException e) {
        LOG.error(e.getMessage(), e);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(e.getExceptionDTO());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ExceptionDTO> handlerHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        LOG.error(e.getMessage(), e);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ExceptionDTO("Error en el JSON de consumo", "FINAL_USER"));
    }
}
package com.meli.mla.configuration.handler;

import com.meli.mla.exception.MsCouponMlaException;
import com.meli.mla.exception.dto.ExceptionDTO;
import com.meli.mla.exception.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    GlobalExceptionHandler globalExceptionHandler;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void handlerMsCouponMlaException() {
        assertNotNull(globalExceptionHandler.handlerMsCouponMlaException(new MsCouponMlaException("Test", new ExceptionDTO(), new Exception())));
    }
    @Test
    void handlerHttpMessageNotReadableException() {
        assertNotNull(globalExceptionHandler.handlerHttpMessageNotReadableException(new HttpMessageNotReadableException("Test")));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
}
package com.meli.mla.configuration.handler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

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
    void handleException() {
        assertNotNull(globalExceptionHandler.handleException(new Exception("Test")));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
}
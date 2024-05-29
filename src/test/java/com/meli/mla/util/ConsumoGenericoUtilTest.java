package com.meli.mla.util;

import com.meli.mla.exception.MsCouponMlaException;
import com.meli.mla.exception.dto.ExceptionDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ConsumoGenericoUtilTest {

    @InjectMocks
    ConsumoGenericoUtil consumoGenericoUtil;

    @Mock
    HttpClient client;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void consumoGenericoApi() throws Exception {
        //Camino Feliz
        assertNotNull(consumoGenericoUtil.consumoGenericoApi("https://api.mercadolibre.com/items/", "MLA811601010"));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
}
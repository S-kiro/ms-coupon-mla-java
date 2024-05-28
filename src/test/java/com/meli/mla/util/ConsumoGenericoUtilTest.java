package com.meli.mla.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class ConsumoGenericoUtilTest {

    @InjectMocks
    ConsumoGenericoUtil consumoGenericoUtil;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void consumoGenericoApi() throws Exception {
        assertNotNull(consumoGenericoUtil.consumoGenericoApi("https://api.mercadolibre.com/items/", "MLA811601010"));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
}
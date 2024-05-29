package com.meli.mla.controller.coupon;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.service.coupon.ICouponService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
class CouponControllerTest {

    @InjectMocks
    CouponController couponController;

    @Mock
    ICouponService couponService;

    AutoCloseable openMocks;
    CouponDTO couponDTORequest;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);

        couponDTORequest = new CouponDTO(new String[]{"MLA1", "MLA2", "MLA3", "MLA4", "MLA5"}, null, null, 500D);
    }

    @Test
    void consultaCompraMaxima() throws Exception {
        doReturn(new CouponDTO()).when(couponService).consultaCompraMaxima(couponDTORequest);
        assertNotNull(couponController.consultaCompraMaxima(couponDTORequest));
    }

    @Test
    void consultaItemsConMasFavoritos() throws Exception {
        doReturn(new ArrayList<>()).when(couponService).consultaItemsConMasFavoritos();
        assertNotNull(couponController.consultaItemsConMasFavoritos());
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
}
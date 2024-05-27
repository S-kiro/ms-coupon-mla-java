package com.meli.mla.controller.addfavorite;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.service.addfavorite.IAddFavoriteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
class AddFavoriteControllerTest {

    @InjectMocks
    AddFavoriteController addFavoriteController;

    @Mock
    IAddFavoriteService addFavoriteService;

    AutoCloseable openMocks;
    CouponDTO couponDTORequest;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        couponDTORequest = new CouponDTO(new String[]{"MLA1", "MLA811601010"}, null, null, "SANTIAGO");
    }

    @Test
    void crearUsuario() throws Exception {
        doReturn(new CouponDTO()).when(addFavoriteService).agregarFavoritosPorUsuario(couponDTORequest);
        assertNotNull(addFavoriteController.crearUsuario(couponDTORequest));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
}
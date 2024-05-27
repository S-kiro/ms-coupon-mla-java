package com.meli.mla.service.coupon;

import static org.mockito.Mockito.*;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.configuration.dto.StatsDTO;
import com.meli.mla.configuration.repository.ItemsLikedForUsersRepository;
import com.meli.mla.util.ConsumoGenericoUtil;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class CouponServiceTest {

    @InjectMocks
    CouponService couponService;

    @Mock
    ConsumoGenericoUtil consumoGenericoUtil;
    @Mock
    ItemsLikedForUsersRepository itemsLikedForUsersRepository;

    AutoCloseable openMocks;
    CouponDTO couponDTORequest;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);

        final String urlConsumoApi = "http://url.de.prueba/";
        ReflectionTestUtils.setField(this.couponService, "urlConsumoApi", urlConsumoApi);

        couponDTORequest = new CouponDTO(new String[]{"MLA1", "MLA2", "MLA3", "MLA4", "MLA5"}, null, 500D, null);
    }

    @Test
    void consultaCompraMaxima() throws Exception {
        // Camino feliz
        doReturn("{\"id\":\"MLA1\",\"price\":100.0}").when(consumoGenericoUtil).consumoGenericoApi(anyString(), eq("MLA1"));
        doReturn("{\"id\":\"MLA2\",\"price\":210.0}").when(consumoGenericoUtil).consumoGenericoApi(anyString(), eq("MLA2"));
        doReturn("{\"id\":\"MLA3\",\"price\":260.0}").when(consumoGenericoUtil).consumoGenericoApi(anyString(), eq("MLA3"));
        doReturn("{\"id\":\"MLA4\",\"price\":80.0}").when(consumoGenericoUtil).consumoGenericoApi(anyString(), eq("MLA4"));
        doReturn("{\"id\":\"MLA5\",\"price\":90.0}").when(consumoGenericoUtil).consumoGenericoApi(anyString(), eq("MLA5"));
        assertNotNull(couponService.consultaCompraMaxima(couponDTORequest));

        // Throw "Se encontraton items repetidos"
        couponDTORequest.setItemsIds(new String[]{"MLA1", "MLA2", "MLA1"});
        Exception ex = assertThrows(Exception.class, () -> {
            couponService.consultaCompraMaxima(couponDTORequest);
        });

        String expectedMessage = "Se encontraton items repetidos";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void consultaItemsConMasFavoritos() throws Exception {
        // Camino feliz
        List<StatsDTO> favs = new ArrayList<>();
        doReturn(favs).when(itemsLikedForUsersRepository).consultaItemsConMasFavoritos();
        assertNotNull(couponService.consultaItemsConMasFavoritos());

        // Throw "Se encontraton items repetidos"
        doThrow(new RuntimeException("Ocurrio un error inesperado al realizar la consulta")).when(itemsLikedForUsersRepository).consultaItemsConMasFavoritos();
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            couponService.consultaItemsConMasFavoritos();
        });

        String expectedMessage = "Ocurrio un error inesperado al realizar la consulta";
        String actualMessage = ex.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @After
    public void tearDown() throws Exception {
        openMocks.close();
    }
}
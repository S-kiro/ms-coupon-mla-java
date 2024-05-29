package com.meli.mla.service.coupon;

import static org.mockito.Mockito.*;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.configuration.dto.StatsDTO;
import com.meli.mla.configuration.embeded.ItemsLikedForUserId;
import com.meli.mla.configuration.model.ItemModel;
import com.meli.mla.configuration.model.UserModel;
import com.meli.mla.configuration.repository.ItemRepository;
import com.meli.mla.configuration.repository.ItemsLikedForUsersRepository;
import com.meli.mla.configuration.repository.UserRepository;
import com.meli.mla.exception.MsCouponMlaException;
import com.meli.mla.exception.dto.ExceptionDTO;
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
    UserRepository userRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    ItemsLikedForUsersRepository itemsLikedForUsersRepository;

    AutoCloseable openMocks;
    CouponDTO couponDTORequest;
    UserModel userModel;
    ItemModel itemModel1;
    ItemModel itemModel2;
    ItemModel itemModel3;
    ItemModel itemModel4;
    ItemModel itemModel5;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);

        final String urlConsumoApi = "http://url.de.prueba/";
        ReflectionTestUtils.setField(this.couponService, "urlConsumoApi", urlConsumoApi);

        userModel = new UserModel("SANTIAGO");
        itemModel1 = new ItemModel("MLA1");
        itemModel2 = new ItemModel("MLA2");
        itemModel3 = new ItemModel("MLA3");
        itemModel4 = new ItemModel("MLA4");
        itemModel5 = new ItemModel("MLA5");
        couponDTORequest = new CouponDTO(new String[]{"MLA1", "MLA2", "MLA3", "MLA4", "MLA5"}, null, "SANTIAGO", 500D);
    }

    @Test
    void consultaCompraMaxima() throws Exception {
        // Camino feliz
        doReturn(true).when(userRepository).existsById("SANTIAGO");
        doReturn(userModel).when(userRepository).getReferenceById("SANTIAGO");

        doReturn(itemModel1).when(itemRepository).getReferenceById("MLA1");
        doReturn(itemModel2).when(itemRepository).getReferenceById("MLA2");
        doReturn(itemModel3).when(itemRepository).getReferenceById("MLA3");
        doReturn(itemModel4).when(itemRepository).getReferenceById("MLA4");
        doReturn(itemModel5).when(itemRepository).getReferenceById("MLA5");

        doReturn(true).when(itemsLikedForUsersRepository).existsById(any());

        doReturn("{\"id\":\"MLA1\",\"price\":100.0}").when(consumoGenericoUtil).consumoGenericoApi(anyString(), eq("MLA1"));
        doReturn("{\"id\":\"MLA2\",\"price\":210.0}").when(consumoGenericoUtil).consumoGenericoApi(anyString(), eq("MLA2"));
        doReturn("{\"id\":\"MLA3\",\"price\":260.0}").when(consumoGenericoUtil).consumoGenericoApi(anyString(), eq("MLA3"));
        doReturn("{\"id\":\"MLA4\",\"price\":80.0}").when(consumoGenericoUtil).consumoGenericoApi(anyString(), eq("MLA4"));
        doReturn("{\"id\":\"MLA5\",\"price\":90.0}").when(consumoGenericoUtil).consumoGenericoApi(anyString(), eq("MLA5"));
        assertNotNull(couponService.consultaCompraMaxima(couponDTORequest));

        // Throw "El usuario suministrado no existe"
        couponDTORequest.setUserId("");
        doReturn(false).when(userRepository).existsById("");
        MsCouponMlaException ex = assertThrows(MsCouponMlaException.class, () -> {
            couponService.consultaCompraMaxima(couponDTORequest);
        });

        String expectedMessage = "El usuario suministrado no existe";
        String actualMessage = ex.getExceptionDTO().getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        // Throw "Los productos suministrados no se encuentran en la lista de favoritos del usuario"
        couponDTORequest.setUserId("SANTIAGO");
        doReturn(false).when(itemsLikedForUsersRepository).existsById(any());
        MsCouponMlaException ex1 = assertThrows(MsCouponMlaException.class, () -> {
            couponService.consultaCompraMaxima(couponDTORequest);
        });

        String expectedMessage1 = "Los productos suministrados no se encuentran en la lista de favoritos del usuario";
        String actualMessage1 = ex1.getExceptionDTO().getMessage();
        assertTrue(actualMessage1.contains(expectedMessage1));

        // Throw "Se encontraton items repetidos"
        couponDTORequest.setItemsIds(new String[]{"MLA1", "MLA2", "MLA1"});
        doReturn(true).when(itemsLikedForUsersRepository).existsById(any());
        MsCouponMlaException ex2 = assertThrows(MsCouponMlaException.class, () -> {
            couponService.consultaCompraMaxima(couponDTORequest);
        });

        String expectedMessage2 = "Se encontraton items repetidos";
        String actualMessage2 = ex2.getExceptionDTO().getMessage();
        assertTrue(actualMessage2.contains(expectedMessage2));

        // Throw "El valor del cupon es insuficiente para los productos suministrados"
        couponDTORequest.setAmount(1D);
        couponDTORequest.setItemsIds(new String[]{"MLA1", "MLA2", "MLA3", "MLA4", "MLA5"});
        MsCouponMlaException ex3 = assertThrows(MsCouponMlaException.class, () -> {
            couponService.consultaCompraMaxima(couponDTORequest);
        });

        String expectedMessage3 = "El valor del cupon es insuficiente para los productos suministrados";
        String actualMessage3 = ex3.getExceptionDTO().getMessage();
        assertTrue(actualMessage3.contains(expectedMessage3));

        // Throw "Ocurrio un error inesperado al realizar la consulta"
        doThrow(new RuntimeException()).when(itemsLikedForUsersRepository).existsById(any());
        MsCouponMlaException ex4 = assertThrows(MsCouponMlaException.class, () -> {
            couponService.consultaCompraMaxima(couponDTORequest);
        });

        String expectedMessage4 = "Ocurrio un error inesperado al realizar la consulta";
        String actualMessage4 = ex4.getExceptionDTO().getMessage();
        assertTrue(actualMessage4.contains(expectedMessage4));
    }

    @Test
    void consultaItemsConMasFavoritos() throws Exception {
        // Camino feliz
        List<StatsDTO> favs = new ArrayList<>();
        doReturn(favs).when(itemsLikedForUsersRepository).consultaItemsConMasFavoritos();
        assertNotNull(couponService.consultaItemsConMasFavoritos());

        // Throw "Se encontraton items repetidos"
        doThrow(new RuntimeException()).when(itemsLikedForUsersRepository).consultaItemsConMasFavoritos();
        MsCouponMlaException ex = assertThrows(MsCouponMlaException.class, () -> {
            couponService.consultaItemsConMasFavoritos();
        });

        String expectedMessage = "Ocurrio un error inesperado";
        String actualMessage = ex.getExceptionDTO().getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @After
    public void tearDown() throws Exception {
        openMocks.close();
    }
}
package com.meli.mla.service.addfavorite;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.configuration.model.ItemModel;
import com.meli.mla.configuration.model.ItemsLikedForUserModel;
import com.meli.mla.configuration.model.UserModel;
import com.meli.mla.configuration.repository.ItemRepository;
import com.meli.mla.configuration.repository.ItemsLikedForUsersRepository;
import com.meli.mla.configuration.repository.UserRepository;
import com.meli.mla.exception.MsCouponMlaException;
import com.meli.mla.util.ConsumoGenericoUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class AddFavoriteServiceTest {

    @InjectMocks
    AddFavoriteService addFavoriteService;

    @Mock
    ConsumoGenericoUtil consumoGenericoUtil;
    @Mock
    ItemRepository itemRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ItemsLikedForUsersRepository itemsLikedForUsersRepository;

    AutoCloseable openMocks;
    CouponDTO couponDTORequest;
    UserModel user;
    ItemModel item;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);

        final String urlConsumoApi = "http://url.de.prueba/";
        ReflectionTestUtils.setField(this.addFavoriteService, "urlConsumoApi", urlConsumoApi);

        user = new UserModel("SANTIAGO");
        item = new ItemModel("MLA811601010");
        couponDTORequest = new CouponDTO(new String[]{"MLA1", "MLA811601010"}, null, "SANTIAGO", null);
    }

    @Test
    void agregarFavoritosPorUsuario() throws Exception {

        //Camino Feliz
        doReturn(true).when(userRepository).existsById(couponDTORequest.getUserId());
        doReturn(user).when(userRepository).getReferenceById(couponDTORequest.getUserId());
        doReturn("{\"message\":\"Not found\"}").when(consumoGenericoUtil).consumoGenericoApi(anyString(), eq("MLA1"));
        doReturn("{\"id\":\"MLA811601010\"}").when(consumoGenericoUtil).consumoGenericoApi(anyString(), eq("MLA811601010"));
        doReturn(item).when(itemRepository).save(any(ItemModel.class));
        doReturn(new ItemsLikedForUserModel()).when(itemsLikedForUsersRepository).save(any());
        assertNotNull(addFavoriteService.agregarFavoritosPorUsuario(couponDTORequest));

        //Primer Catch
        doThrow(new RuntimeException("")).when(itemRepository).save(any(ItemModel.class));
        assertNotNull(addFavoriteService.agregarFavoritosPorUsuario(couponDTORequest));

        //Segundo Catch
        couponDTORequest.setUserId("MIGUEL");
        doReturn(false).when(userRepository).existsById(couponDTORequest.getUserId());
        MsCouponMlaException ex = assertThrows(MsCouponMlaException.class, () -> {
            addFavoriteService.agregarFavoritosPorUsuario(couponDTORequest);
        });

        String expectedMessage = "No se encontro el usuario solicitado";
        String actualMessage = ex.getExceptionDTO().getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
}
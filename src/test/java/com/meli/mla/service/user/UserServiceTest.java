package com.meli.mla.service.user;

import com.meli.mla.configuration.dto.UserDTO;
import com.meli.mla.configuration.model.UserModel;
import com.meli.mla.configuration.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    AutoCloseable openMocks;
    UserDTO userDTO;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);

        userDTO = new UserDTO("MIGUEL");
    }

    @Test
    void createUser() throws MsCouponMlaException {
        //Camino Feliz
        doReturn(new UserModel()).when(userRepository).save(any(UserModel.class));
        assertNotNull(userService.createUser(userDTO));

        //Catch
        doThrow(new RuntimeException()).when(userRepository).save(any(UserModel.class));
        MsCouponMlaException ex = assertThrows(MsCouponMlaException.class, () -> {
            userService.createUser(userDTO);
        });

        String expectedMessage = "Ocurrio un error inesperado al realizar la consulta";
        String actualMessage = ex.getExceptionDTO().getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
}
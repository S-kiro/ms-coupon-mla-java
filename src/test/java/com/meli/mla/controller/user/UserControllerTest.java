package com.meli.mla.controller.user;

import com.meli.mla.configuration.dto.UserDTO;
import com.meli.mla.service.user.IUserService;
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
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    IUserService userService;

    AutoCloseable openMocks;
    UserDTO user;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        user = new UserDTO("MIGUEL");
    }

    @Test
    void crearUsuario() throws Exception {
        doReturn("Usuario creado con exito").when(userService).createUser(user);
        assertNotNull(userController.crearUsuario(user));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
}
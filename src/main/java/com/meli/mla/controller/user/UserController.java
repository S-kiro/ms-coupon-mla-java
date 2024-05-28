package com.meli.mla.controller.user;

import com.meli.mla.configuration.dto.UserDTO;
import com.meli.mla.exception.MsCouponMlaException;
import com.meli.mla.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements IUserController {

    @Autowired
    private IUserService userService;

    @Override
    public ResponseEntity<String> crearUsuario(@RequestBody UserDTO userDTO) throws MsCouponMlaException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO));
    }
}

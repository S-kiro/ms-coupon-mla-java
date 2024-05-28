package com.meli.mla.controller.user;

import com.meli.mla.configuration.dto.UserDTO;
import com.meli.mla.exception.MsCouponMlaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface IUserController {

    @PostMapping("/create")
    ResponseEntity<String> crearUsuario(@RequestBody UserDTO userDTO) throws MsCouponMlaException;
}

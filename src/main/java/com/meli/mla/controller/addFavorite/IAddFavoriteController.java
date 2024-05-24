package com.meli.mla.controller.addFavorite;

import com.meli.mla.configuration.dto.CouponDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/addFavoriteItem")
public interface IAddFavoriteController {

    @PostMapping("/toUser")
    ResponseEntity<CouponDTO> crearUsuario(@RequestBody CouponDTO couponRequestDTO) throws Exception;
}

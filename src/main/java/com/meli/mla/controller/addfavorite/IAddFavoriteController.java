package com.meli.mla.controller.addfavorite;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.exception.MsCouponMlaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/addFavoriteItem")
public interface IAddFavoriteController {

    @PostMapping("/toUser")
    ResponseEntity<CouponDTO> agregarFavoritosPorUsuario(@RequestBody CouponDTO couponRequestDTO) throws MsCouponMlaException;
}

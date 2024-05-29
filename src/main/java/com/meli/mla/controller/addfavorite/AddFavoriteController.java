package com.meli.mla.controller.addfavorite;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.exception.MsCouponMlaException;
import com.meli.mla.service.addfavorite.IAddFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddFavoriteController implements IAddFavoriteController {

    @Autowired
    private IAddFavoriteService addFavoriteService;

    @Override
    public ResponseEntity<CouponDTO> agregarFavoritosPorUsuario(@RequestBody CouponDTO couponRequestDTO) throws MsCouponMlaException {
        return ResponseEntity.status(HttpStatus.OK).body(addFavoriteService.agregarFavoritosPorUsuario(couponRequestDTO));
    }
}

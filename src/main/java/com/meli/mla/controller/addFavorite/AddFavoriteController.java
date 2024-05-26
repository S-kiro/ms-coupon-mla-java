package com.meli.mla.controller.addFavorite;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.service.addFavorite.IAddFavoriteService;
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
    public ResponseEntity<CouponDTO> crearUsuario(@RequestBody CouponDTO couponRequestDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(addFavoriteService.agregarFavoritosPorUsuario(couponRequestDTO));
    }
}

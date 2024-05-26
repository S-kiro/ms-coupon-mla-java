package com.meli.mla.service.addFavorite;

import com.meli.mla.configuration.dto.CouponDTO;

import java.io.IOException;

public interface IAddFavoriteService {

    CouponDTO agregarFavoritosPorUsuario(CouponDTO couponRequestDTO) throws Exception;
}

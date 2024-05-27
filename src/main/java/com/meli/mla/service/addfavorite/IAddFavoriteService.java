package com.meli.mla.service.addfavorite;

import com.meli.mla.configuration.dto.CouponDTO;

public interface IAddFavoriteService {

    CouponDTO agregarFavoritosPorUsuario(CouponDTO couponRequestDTO) throws Exception;
}

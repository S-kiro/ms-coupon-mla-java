package com.meli.mla.service.addfavorite;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.exception.MsCouponMlaException;

public interface IAddFavoriteService {

    CouponDTO agregarFavoritosPorUsuario(CouponDTO couponRequestDTO) throws MsCouponMlaException;
}

package com.meli.mla.service.coupon;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.configuration.dto.StatsDTO;
import com.meli.mla.exception.MsCouponMlaException;

import java.util.List;

public interface ICouponService {

    CouponDTO consultaCompraMaxima(CouponDTO couponDTORequest) throws MsCouponMlaException;

    List<StatsDTO> consultaItemsConMasFavoritos() throws MsCouponMlaException;
}

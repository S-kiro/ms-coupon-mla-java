package com.meli.mla.service.coupon;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.configuration.dto.StatsDTO;

import java.io.IOException;
import java.util.List;

public interface ICouponService {

    CouponDTO consultaCompraMaxima(CouponDTO couponDTORequest) throws Exception;

    List<StatsDTO> consultaItemsConMasFavoritos() throws Exception;
}

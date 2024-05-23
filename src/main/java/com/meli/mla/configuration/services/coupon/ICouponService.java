package com.meli.mla.configuration.services.coupon;

import com.meli.mla.configuration.dto.CouponDTO;

import java.io.IOException;

public interface ICouponService {

    CouponDTO consultaCompraMaxima(CouponDTO couponDTORequest) throws IOException, InterruptedException;
}

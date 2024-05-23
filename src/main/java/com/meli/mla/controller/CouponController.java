package com.meli.mla.controller;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.configuration.services.coupon.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CouponController implements ICouponController {

    @Autowired
    private CouponService couponService;

    @Override
    public ResponseEntity<CouponDTO> consultaCompraMaxima(CouponDTO couponDTORequest) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(couponService.consultaCompraMaxima(couponDTORequest));
    }
}

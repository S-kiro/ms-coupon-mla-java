package com.meli.mla.controller.coupon;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.configuration.dto.StatsDTO;
import com.meli.mla.exception.MsCouponMlaException;
import com.meli.mla.service.coupon.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CouponController implements ICouponController {

    @Autowired
    private ICouponService couponService;

    @Override
    public ResponseEntity<CouponDTO> consultaCompraMaxima(CouponDTO couponDTORequest) throws MsCouponMlaException {
        return ResponseEntity.status(HttpStatus.OK).body(couponService.consultaCompraMaxima(couponDTORequest));
    }

    @Override
    public ResponseEntity<List<StatsDTO>> consultaItemsConMasFavoritos() throws MsCouponMlaException {
        return ResponseEntity.status(HttpStatus.OK).body(couponService.consultaItemsConMasFavoritos());
    }
}

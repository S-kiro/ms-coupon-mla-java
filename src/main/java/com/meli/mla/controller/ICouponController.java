package com.meli.mla.controller;

import com.meli.mla.configuration.dto.CouponDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public interface ICouponController {

    /**
     * Servicio que reciba una lista de productos y valor de un cupon para realizar una compra de mayor valor posible
     */
    @PostMapping("/coupon")
    ResponseEntity<CouponDTO> consultaCompraMaxima(@RequestBody CouponDTO couponDTORequest) throws Exception;
}
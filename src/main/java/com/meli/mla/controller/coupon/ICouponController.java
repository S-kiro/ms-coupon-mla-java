package com.meli.mla.controller.coupon;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.configuration.dto.StatsDTO;
import com.meli.mla.exception.MsCouponMlaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/coupon")
public interface ICouponController {

    @PostMapping("")
    ResponseEntity<CouponDTO> consultaCompraMaxima(@RequestBody CouponDTO couponDTORequest) throws MsCouponMlaException;

    @GetMapping("/stats")
    ResponseEntity<List<StatsDTO>> consultaItemsConMasFavoritos() throws MsCouponMlaException;
}
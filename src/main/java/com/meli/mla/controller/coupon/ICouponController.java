package com.meli.mla.controller.coupon;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.configuration.dto.StatsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/coupon")
public interface ICouponController {

    /**
     * Servicio que reciba una lista de productos y valor de un cupon para realizar una compra de mayor valor posible
     */
    @PostMapping("")
    ResponseEntity<CouponDTO> consultaCompraMaxima(@RequestBody CouponDTO couponDTORequest) throws Exception;

    @GetMapping("/stats")
    ResponseEntity<List<StatsDTO>> consultaItemsConMasFavoritos() throws Exception;
}
package com.meli.mla.configuration.services.coupon;

import com.google.gson.Gson;
import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.configuration.dto.ItemDTO;
import com.meli.mla.configuration.util.ConsumoGenericoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CouponService implements ICouponService {

    @Autowired
    ConsumoGenericoUtil consumoGenericoUtil;

    @Value("${url.consumo_items}")
    private String urlConsumoApi;

    public CouponDTO consultaCompraMaxima(CouponDTO couponDTORequest) throws IOException, InterruptedException {

        String rawJson = consumoGenericoUtil.consumoGenericoApi(urlConsumoApi, String.join(",", couponDTORequest.getItemsIds()));
        ItemDTO items = new Gson().fromJson(rawJson, ItemDTO.class);

        return couponDTORequest;
    }
}

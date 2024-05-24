package com.meli.mla.configuration.service.coupon;

import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.configuration.dto.ItemDTO;
import com.meli.mla.configuration.dto.StatsDTO;
import com.meli.mla.configuration.util.ConsumoGenericoUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CouponService implements ICouponService {

    @Autowired
    ConsumoGenericoUtil consumoGenericoUtil;

    @Value("${url.consumo_items}")
    private String urlConsumoApi;

    public CouponDTO consultaCompraMaxima(CouponDTO couponDTORequest) throws IOException, InterruptedException {

        List<ItemDTO> items = new ArrayList<>();
        //llamado a la masiva

        /*String rawJson = "";
        for (String item : couponDTORequest.getItemsIds()) {
            //rawJson = consumoGenericoUtil.consumoGenericoApi(urlConsumoApi, String.join(",", couponDTORequest.getItemsIds()));
            rawJson = consumoGenericoUtil.consumoGenericoApi(urlConsumoApi, item);
            items.add(new Gson().fromJson(rawJson, ItemDTO.class));
        }*/

        items.add(new ItemDTO("MLA1", 100D));
        items.add(new ItemDTO("MLA2", 210D));
        items.add(new ItemDTO("MLA3", 260D));
        items.add(new ItemDTO("MLA4", 80D));
        items.add(new ItemDTO("MLA5", 90D));

        double total = items.stream().mapToDouble(ItemDTO::getPrice).sum();

        while (total > couponDTORequest.getAmount()) {
            double minVal = Double.MAX_VALUE;
            double difMin = Double.MAX_VALUE;
            String itemId = "";
            double exc = total - couponDTORequest.getAmount();

            for (ItemDTO item : items) {
                double num = Math.abs(item.getPrice() - exc);
                if (num < difMin) {
                    difMin = num;
                    minVal = item.getPrice();
                    itemId = item.getId();
                }
            }

            couponDTORequest.setItemsIds(ArrayUtils.removeElement(couponDTORequest.getItemsIds(), itemId));
            total -= minVal;
        }

        couponDTORequest.setAmount(total);
        return couponDTORequest;
    }

    public List<StatsDTO> consultaItemsConMasFavoritos() {

        return null;
    }
}

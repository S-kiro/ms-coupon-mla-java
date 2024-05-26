package com.meli.mla.service.coupon;

import com.google.gson.Gson;
import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.configuration.dto.ItemDTO;
import com.meli.mla.configuration.dto.StatsDTO;
import com.meli.mla.configuration.repository.ItemsLikedForUsersRepository;
import com.meli.mla.configuration.util.ConsumoGenericoUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@Service
public class CouponService implements ICouponService {

    @Autowired
    private ConsumoGenericoUtil consumoGenericoUtil;

    @Autowired
    private ItemsLikedForUsersRepository itemsLikedForUsersRepository;

    @Value("${url.consumo_items}")
    private String urlConsumoApi;

    public CouponDTO consultaCompraMaxima(CouponDTO couponDTORequest) throws Exception {

        List<ItemDTO> items = new ArrayList<>();
        String rawJson = "";
        for (String item : couponDTORequest.getItemsIds()) {
            //rawJson = consumoGenericoUtil.consumoGenericoApi(urlConsumoApi, String.join(",", couponDTORequest.getItemsIds()));
            rawJson = consumoGenericoUtil.consumoGenericoApi(urlConsumoApi, item);
            items.add(new Gson().fromJson(rawJson, ItemDTO.class));
        }

        String[] arrayItems = (String[]) items.stream().map(ItemDTO::getId).toArray();
        for (ItemDTO item : items) {
            BitSet bs = ArrayUtils.indexesOf(arrayItems, item.getId());
            if (bs.cardinality() > 1) {
                throw new Exception("Se encontraton items repetidos");
            }
        }

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

    @Override
    public List<StatsDTO> consultaItemsConMasFavoritos() throws Exception {
        try {
            return itemsLikedForUsersRepository.consultaItemsConMasFavoritos();
        } catch (Exception e) {
            throw new Exception("Ocurrio un error inesperado al realizar la consulta");
        }
    }
}

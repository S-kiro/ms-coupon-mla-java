package com.meli.mla.service.coupon;

import com.google.gson.Gson;
import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.configuration.dto.ItemDTO;
import com.meli.mla.configuration.dto.StatsDTO;
import com.meli.mla.configuration.embeded.ItemsLikedForUserId;
import com.meli.mla.configuration.model.ItemModel;
import com.meli.mla.configuration.model.UserModel;
import com.meli.mla.configuration.repository.ItemRepository;
import com.meli.mla.configuration.repository.ItemsLikedForUsersRepository;
import com.meli.mla.configuration.repository.UserRepository;
import com.meli.mla.exception.MsCouponMlaException;
import com.meli.mla.exception.dto.ExceptionDTO;
import com.meli.mla.util.ConsumoGenericoUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@Service
public class CouponService implements ICouponService {

    @Autowired
    private ConsumoGenericoUtil consumoGenericoUtil;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemsLikedForUsersRepository itemsLikedForUsersRepository;

    @Value("${url.consumo_items}")
    private String urlConsumoApi;

    private final String className = getClass().getName();

    public CouponDTO consultaCompraMaxima(CouponDTO couponDTORequest) throws MsCouponMlaException {

        UserModel tempUser;
        if (userRepository.existsById(couponDTORequest.getUserId())) {
            tempUser = userRepository.getReferenceById(couponDTORequest.getUserId());
        } else {
            throw new MsCouponMlaException("User not exist: " + className,
                new ExceptionDTO("El usuario suministrado no existe", "FINAL_USER"));
        }

        List<ItemDTO> items = new ArrayList<>();
        String rawJson = "";
        for (String item : couponDTORequest.getItemsIds()) {
            rawJson = consumoGenericoUtil.consumoGenericoApi(urlConsumoApi, item);
            ItemDTO tempItem = new Gson().fromJson(rawJson, ItemDTO.class);
            if (validateIsFavorite(tempUser, tempItem.getId())) {
                items.add(tempItem);
            }
        }

        if (ObjectUtils.isEmpty(items)) {
            throw new MsCouponMlaException("Items list empty: " + className,
                new ExceptionDTO("Los productos suministrados no se encuentran en la lista de favoritos del usuario", "FINAL_USER"));
        }

        validateRecurrentItems(items);

        double total = items.stream().mapToDouble(ItemDTO::getPrice).sum();
        while (total > couponDTORequest.getAmount()) {
            double minVal = Double.MAX_VALUE;
            double difMin = Double.MAX_VALUE;
            ItemDTO itemId = null;
            double exc = total - couponDTORequest.getAmount();

            for (ItemDTO item : items) {
                double num = roundTwoDecimals(Math.abs(item.getPrice() - exc));
                if (num < difMin) {
                    difMin = num;
                    minVal = item.getPrice();
                    itemId = item;
                }
            }

            items.remove(itemId);
            if (ObjectUtils.isEmpty(items)) {
                throw new MsCouponMlaException("Insuficient coupon amout: " + className,
                    new ExceptionDTO("El valor del cupon es insuficiente para los productos suministrados", "SYSTEM"));
            } else {
                couponDTORequest.setItemsIds(items.stream().map(ItemDTO::getId).toArray(String[]::new));
                total -= minVal;
            }
        }

        couponDTORequest.setAmount(roundTwoDecimals(total));
        return couponDTORequest;
    }

    @Override
    public List<StatsDTO> consultaItemsConMasFavoritos() throws MsCouponMlaException {
        try {
            return itemsLikedForUsersRepository.consultaItemsConMasFavoritos();
        } catch (RuntimeException e) {
            throw new MsCouponMlaException("Error consulting favorites: " + className,
                new ExceptionDTO("Ocurrio un error inesperado al realizar la consulta", "SQL"),
                e);
        }
    }

    private double roundTwoDecimals(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private boolean validateIsFavorite(UserModel user, String item) throws MsCouponMlaException {
        try {
            ItemModel tempItem = itemRepository.getReferenceById(item);
            return itemsLikedForUsersRepository.existsById(new ItemsLikedForUserId(tempItem, user));
        } catch (RuntimeException e) {
            throw new MsCouponMlaException("Error consultin DB: " + className,
                new ExceptionDTO("Ocurrio un error inesperado al realizar la consulta", "DB"),
                e);
        }
    }

    private void validateRecurrentItems(List<ItemDTO> items) throws MsCouponMlaException {
        Object[] arrayItems = items.stream().map(ItemDTO::getId).toArray();
        for (ItemDTO item : items) {
            BitSet bs = ArrayUtils.indexesOf(arrayItems, item.getId());
            if (bs.cardinality() > 1) {
                throw new MsCouponMlaException("Recurrent id: " + className,
                        new ExceptionDTO("Se encontraton items repetidos", "FINAL_USER"));
            }
        }
    }
}

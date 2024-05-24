package com.meli.mla.configuration.service.addFavorite;

import com.google.gson.Gson;
import com.meli.mla.configuration.dto.CouponDTO;
import com.meli.mla.configuration.embeded.ItemsLikedForUserId;
import com.meli.mla.configuration.model.ItemModel;
import com.meli.mla.configuration.model.ItemsLikedForUserModel;
import com.meli.mla.configuration.model.UserModel;
import com.meli.mla.configuration.repository.ItemRepository;
import com.meli.mla.configuration.repository.ItemsLikedForUsersRepository;
import com.meli.mla.configuration.repository.UserRepository;
import com.meli.mla.configuration.util.ConsumoGenericoUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddFavoriteService implements IAddFavoriteService {

    @Autowired
    private ConsumoGenericoUtil consumoGenericoUtil;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemsLikedForUsersRepository itemsLikedForUsersRepository;

    @Value("${url.consumo_items}")
    private String urlConsumoApi;

    @Override
    public CouponDTO agregarFavoritosPorUsuario(CouponDTO couponRequestDTO) throws Exception {

        UserModel user = userRepository.getReferenceById(couponRequestDTO.getUserId());

        if (StringUtils.isNotBlank(user.getUserId())) {
            List<ItemModel> items = new ArrayList<>();
            List<String> itemsNoEncontrados = new ArrayList<>();
            String rawJson = "";
            for (String item : couponRequestDTO.getItemsIds()) {
                rawJson = consumoGenericoUtil.consumoGenericoApi(urlConsumoApi, item);
                if (rawJson.startsWith("{" + '"' + "id")) {
                    try {
                        items.add(itemRepository.save(new Gson().fromJson(rawJson, ItemModel.class)));
                    } catch (Exception e) {
                        itemsNoEncontrados.add(item);
                    }
                } else if (rawJson.startsWith("{" + '"' + "message")) {
                    couponRequestDTO.setItemsIds(ArrayUtils.removeElement(couponRequestDTO.getItemsIds(), item));
                    itemsNoEncontrados.add(item);
                }
            }

            for (ItemModel item : items) {
                itemsLikedForUsersRepository.save(new ItemsLikedForUserModel(new ItemsLikedForUserId(item, user)));
            }

            couponRequestDTO.setItemsIdsNoEncontrados(itemsNoEncontrados.toArray(new String[0]));
            couponRequestDTO.setUserId(user.getUserId());

        } else {
            throw new Exception("No se encontro el usuario solicitado");
        }

        return couponRequestDTO;
    }
}

package com.meli.mla.configuration.embeded;

import com.meli.mla.configuration.model.ItemModel;
import com.meli.mla.configuration.model.UserModel;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemsLikedForUserId implements Serializable {

    @JoinColumn(name = "item_id")
    @NonNull
    @ManyToOne(targetEntity = ItemModel.class, fetch = FetchType.LAZY)
    private ItemModel itemId;

    @JoinColumn(name = "user_id")
    @NonNull
    @ManyToOne(targetEntity = UserModel.class, fetch = FetchType.LAZY)
    private UserModel userId;
}

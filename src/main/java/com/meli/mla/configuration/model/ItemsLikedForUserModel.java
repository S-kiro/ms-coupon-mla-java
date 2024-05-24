package com.meli.mla.configuration.model;

import com.meli.mla.configuration.embeded.ItemsLikedForUserId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "items_liked_for_user")
public class ItemsLikedForUserModel {

    @EmbeddedId
    private ItemsLikedForUserId id;
}

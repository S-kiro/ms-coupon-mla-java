package com.meli.mla.configuration.repository;

import com.meli.mla.configuration.dto.StatsDTO;
import com.meli.mla.configuration.embeded.ItemsLikedForUserId;
import com.meli.mla.configuration.model.ItemsLikedForUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsLikedForUsersRepository extends JpaRepository<ItemsLikedForUserModel, ItemsLikedForUserId> {

    @Query(value = "SELECT new com.meli.mla.configuration.dto.StatsDTO(i.id.itemId.id, COUNT(i)) FROM ItemsLikedForUserModel i GROUP BY i.id.itemId ORDER BY COUNT(i) DESC limit 5")
    List<StatsDTO> consultaItemsConMasFavoritos();
}

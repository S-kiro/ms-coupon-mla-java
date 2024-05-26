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

    //@Query(value = "SELECT item_id, COUNT(*) FROM MERCADO_LIBRE.items_liked_for_user GROUP BY item_id ORDER BY COUNT(*) LIMIT 5 ", nativeQuery = true)
    @Query(value = "select new com.meli.mla.configuration.dto.StatsDTO(i.id.itemId.id, COUNT(i)) from ItemsLikedForUserModel i group by i.id.itemId order by COUNT(i) limit 5")
    List<StatsDTO> consultaItemsConMasFavoritos();
}

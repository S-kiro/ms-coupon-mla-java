package com.meli.mla.configuration.repository;

import com.meli.mla.configuration.model.ItemsLikedForUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsLikedForUsersRepository extends JpaRepository<ItemsLikedForUserModel, Integer> {


}

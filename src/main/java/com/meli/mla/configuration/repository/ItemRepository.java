package com.meli.mla.configuration.repository;

import com.meli.mla.configuration.model.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel, String> {
}

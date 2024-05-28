package com.meli.mla.configuration.repository;

import com.meli.mla.configuration.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
}

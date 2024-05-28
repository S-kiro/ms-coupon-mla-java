package com.meli.mla.service.user;

import com.meli.mla.configuration.dto.UserDTO;
import com.meli.mla.exception.MsCouponMlaException;

public interface IUserService {

    String createUser(UserDTO userDto) throws MsCouponMlaException;
}

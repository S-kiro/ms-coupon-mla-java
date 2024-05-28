package com.meli.mla.service.user;

import com.meli.mla.configuration.dto.UserDTO;
import com.meli.mla.configuration.model.UserModel;
import com.meli.mla.configuration.repository.UserRepository;
import com.meli.mla.exception.MsCouponMlaException;
import com.meli.mla.exception.dto.ExceptionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    private final String className = getClass().getName();

    public String createUser(UserDTO userDto) throws MsCouponMlaException {

        ModelMapper modelMapper = new ModelMapper();
        UserModel user = modelMapper.map(userDto, UserModel.class);

        try {
            userRepository.save(user);
            return "Usuario creado con exito";
        } catch (RuntimeException e) {
            throw new MsCouponMlaException("Error creating user: " + className,
                new ExceptionDTO("Ocurrio un error inesperado al realizar la consulta", "SQL"),
                e);
        }
    }
}
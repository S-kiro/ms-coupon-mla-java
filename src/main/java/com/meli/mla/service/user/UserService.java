package com.meli.mla.service.user;

import com.meli.mla.configuration.dto.UserDTO;
import com.meli.mla.configuration.model.UserModel;
import com.meli.mla.configuration.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    public String createUser(UserDTO userDto) {

        ModelMapper modelMapper = new ModelMapper();
        UserModel user = modelMapper.map(userDto, UserModel.class);

        try {
            userRepository.save(user);
            return "Usuario creado con exito";
        } catch (RuntimeException e) {
            return "Fallo al crear el usuario";
        }
    }
}
package com.david.smart_home.service;

import org.springframework.stereotype.Service;

import com.david.smart_home.dto.UserDTO;
import com.david.smart_home.exception.UserNotFoundException;
import com.david.smart_home.mapper.UserMapper;
import com.david.smart_home.model.User;
import com.david.smart_home.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public String getUserPassword(String username){
        User usuarioExistente = userRepository.findById(username).orElseThrow(() -> new UserNotFoundException("No existe el usuario " + username));
        return usuarioExistente.getPassword();
    }

    public UserDTO getUser(String username) {
        return userRepository.findById(username)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException("No existe el usuario " + username));
    }
}

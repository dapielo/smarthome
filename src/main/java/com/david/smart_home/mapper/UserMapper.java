package com.david.smart_home.mapper;

import org.springframework.stereotype.Component;
import com.david.smart_home.dto.UserDTO;
import com.david.smart_home.model.User;

@Component
public class UserMapper {

    public User toEntity(UserDTO dto) {
        return new User(dto.username(), dto.password());
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getUsername(), user.getPassword());
    }

}

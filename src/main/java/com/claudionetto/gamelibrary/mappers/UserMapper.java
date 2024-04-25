package com.claudionetto.gamelibrary.mappers;

import com.claudionetto.gamelibrary.dtos.requests.UserRequestDTO;
import com.claudionetto.gamelibrary.dtos.responses.UserResponseDTO;
import com.claudionetto.gamelibrary.models.User;

public class UserMapper {

    public static UserResponseDTO toUserResponseDTO(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public static User toUser(UserRequestDTO userRequestDTO){

        User user = new User();
        user.setName(userRequestDTO.name());
        user.setUsername(userRequestDTO.username());
        user.setPassword(userRequestDTO.password());
        user.setEmail(userRequestDTO.email());

        return user;
    }

}

package com.claudionetto.gamelibrary.services;

import com.claudionetto.gamelibrary.dtos.UserRequestDTO;
import com.claudionetto.gamelibrary.dtos.UserResponseDTO;
import com.claudionetto.gamelibrary.exceptions.NotFoundException;
import com.claudionetto.gamelibrary.mappers.UserMapper;
import com.claudionetto.gamelibrary.models.User;
import com.claudionetto.gamelibrary.repositories.UserRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserResponseDTO> findAll(Pageable pageable){
        return userRepository.findAll(pageable).map(UserMapper::toUserResponseDTO);
    }

    public UserResponseDTO findById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));
        return UserMapper.toUserResponseDTO(user);
    }

    public Long save(UserRequestDTO userRequestDTO){
        return userRepository.save(UserMapper.toUser(userRequestDTO)).getId();
    }

    public void update(Long id, UserRequestDTO userRequestDTO){

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));

        if(userRequestDTO.name() != null) user.setName(userRequestDTO.name());
        if(userRequestDTO.email() != null) user.setEmail(userRequestDTO.email());
        if(userRequestDTO.username() != null) user.setUsername(userRequestDTO.username());
        if(userRequestDTO.password() != null) user.setPassword(userRequestDTO.password());

        userRepository.save(user);
    }


}

package com.claudionetto.gamelibrary.services;

import com.claudionetto.gamelibrary.dtos.UserRequestDTO;
import com.claudionetto.gamelibrary.dtos.UserResponseDTO;
import com.claudionetto.gamelibrary.exceptions.NotFoundException;
import com.claudionetto.gamelibrary.mappers.UserMapper;
import com.claudionetto.gamelibrary.models.User;
import com.claudionetto.gamelibrary.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User findByIdOrThrowNotFoundException(long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));
    }

    public Page<UserResponseDTO> findAll(Pageable pageable){
        return userRepository.findAll(pageable).map(UserMapper::toUserResponseDTO);
    }

    public UserResponseDTO findById(Long id){
        User user = findByIdOrThrowNotFoundException(id);
        return UserMapper.toUserResponseDTO(user);
    }

    public Long save(UserRequestDTO userRequestDTO){
        return userRepository.save(UserMapper.toUser(userRequestDTO)).getId();
    }

    public void update(Long id, UserRequestDTO userRequestDTO){

        User user = findByIdOrThrowNotFoundException(id);

        if(userRequestDTO.name() != null) user.setName(userRequestDTO.name());
        if(userRequestDTO.email() != null) user.setEmail(userRequestDTO.email());
        if(userRequestDTO.username() != null) user.setUsername(userRequestDTO.username());
        if(userRequestDTO.password() != null) user.setPassword(userRequestDTO.password());

        userRepository.save(user);
    }

    public void delete(Long id){

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));
        userRepository.delete(user);

    }


}

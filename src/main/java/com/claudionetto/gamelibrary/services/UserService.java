package com.claudionetto.gamelibrary.services;

import com.claudionetto.gamelibrary.dtos.requests.UserRequestDTO;
import com.claudionetto.gamelibrary.dtos.responses.UserResponseDTO;
import com.claudionetto.gamelibrary.enums.Role;
import com.claudionetto.gamelibrary.exceptions.UserNotFoundException;
import com.claudionetto.gamelibrary.exceptions.UserAlreadyExistsException;
import com.claudionetto.gamelibrary.mappers.UserMapper;
import com.claudionetto.gamelibrary.models.User;
import com.claudionetto.gamelibrary.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private User findByIdOrThrowNotFoundException(long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    public Page<UserResponseDTO> findAll(Pageable pageable){
        return userRepository.findAll(pageable).map(UserMapper::toUserResponseDTO);
    }

    public UserResponseDTO findById(Long id){
        User user = findByIdOrThrowNotFoundException(id);
        return UserMapper.toUserResponseDTO(user);
    }

    public Long save(UserRequestDTO userRequestDTO){

        if (userRepository.findByUsername(userRequestDTO.username()).isPresent()){
            throw new UserAlreadyExistsException("Username " + userRequestDTO.username() + " already exists");
        }

        if (userRepository.findByEmail(userRequestDTO.email()).isPresent()){
            throw new UserAlreadyExistsException("Email " + userRequestDTO.email() + " already exists");
        }


        User user = UserMapper.toUser(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.password()));
        user.setRoles(List.of(Role.USER));
        return userRepository.save(user).getId();
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

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        userRepository.delete(user);

    }


}

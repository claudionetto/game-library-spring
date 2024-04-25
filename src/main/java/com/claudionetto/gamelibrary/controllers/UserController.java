package com.claudionetto.gamelibrary.controllers;


import com.claudionetto.gamelibrary.dtos.requests.UserRequestDTO;
import com.claudionetto.gamelibrary.dtos.responses.UserResponseDTO;
import com.claudionetto.gamelibrary.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(Pageable pageable){
        Page<UserResponseDTO> pageResponse = userService.findAll(pageable);
        return ResponseEntity.ok(pageResponse);
    }

     @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> find(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(userService.findById(id));
     }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody UserRequestDTO userRequestDTO){
        Long id = userService.save(userRequestDTO);
        return ResponseEntity.created(URI.create("/users/" + id)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable(name = "id") Long id, @RequestBody UserRequestDTO userRequestDTO){
        userService.update(id, userRequestDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id){
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }
}

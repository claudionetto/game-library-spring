package com.claudionetto.gamelibrary.controllers;

import com.claudionetto.gamelibrary.dtos.requests.AuthRequestDTO;
import com.claudionetto.gamelibrary.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationService authenticationService, AuthenticationManager authenticationManager) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public String authenticate(@Valid @RequestBody AuthRequestDTO authRequestDTO){

        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                authRequestDTO.username(), authRequestDTO.password());

        Authentication auth = authenticationManager.authenticate(usernamePassword);

        return authenticationService.authenticate(auth);
    }

}

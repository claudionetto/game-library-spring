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

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public String authenticate(@Valid @RequestBody AuthRequestDTO authRequestDTO){
        return authenticationService.authenticate(authRequestDTO);
    }

}

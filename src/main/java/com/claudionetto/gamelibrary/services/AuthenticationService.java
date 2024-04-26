package com.claudionetto.gamelibrary.services;

import com.claudionetto.gamelibrary.dtos.requests.AuthRequestDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    public String authenticate(AuthRequestDTO authRequestDTO){

        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                authRequestDTO.username(), authRequestDTO.password());

        Authentication authentication = authenticationManager.authenticate(usernamePassword);

        return jwtService.generateToken(authentication);
    }

}

package com.claudionetto.gamelibrary.services;

import com.claudionetto.gamelibrary.dtos.requests.AuthRequestDTO;
import jakarta.servlet.http.HttpServletResponse;
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
    public void authenticate(AuthRequestDTO authRequestDTO, HttpServletResponse response){

        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                authRequestDTO.username(), authRequestDTO.password());

        Authentication authentication = authenticationManager.authenticate(usernamePassword);

       jwtService.authenticate(authentication, response);
    }

}

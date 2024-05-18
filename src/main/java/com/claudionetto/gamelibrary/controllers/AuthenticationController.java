package com.claudionetto.gamelibrary.controllers;

import com.claudionetto.gamelibrary.dtos.requests.AuthRequestDTO;
import com.claudionetto.gamelibrary.services.AuthenticationService;
import com.claudionetto.gamelibrary.services.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Authentication Controller")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Auth with Login and Password, and returns Access Token and Refresh Token")
    @PostMapping
    public ResponseEntity<Void> authenticate(@Valid @RequestBody AuthRequestDTO authRequestDTO, HttpServletResponse response){
        authenticationService.authenticate(authRequestDTO, response);
        return ResponseEntity.ok().build();
    }

    @PostMapping ("/refresh-token")
    public ResponseEntity<Void> getAccessToken(HttpServletRequest request, HttpServletResponse response){

        jwtService.generateTokensWithRefreshToken(request, response);

        return ResponseEntity.ok().build();
    }

}

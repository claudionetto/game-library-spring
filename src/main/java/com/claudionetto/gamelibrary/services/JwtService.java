package com.claudionetto.gamelibrary.services;

import com.claudionetto.gamelibrary.models.RefreshToken;
import com.claudionetto.gamelibrary.models.User;
import com.claudionetto.gamelibrary.repositories.RefreshTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtService(JwtEncoder jwtEncoder, RefreshTokenRepository refreshTokenRepository) {
        this.jwtEncoder = jwtEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public User getUserByAuthentication(Authentication authentication){
        return (User) authentication.getPrincipal();
    }

    public void authenticate(Authentication authentication, HttpServletResponse response){
        User user = getUserByAuthentication(authentication);
        generateTokens(user, response);
    }

    public void generateTokens(User user, HttpServletResponse response){

        String refreshToken = createRefreshToken(user);
        String accessToken = createAccessToken(user);

        createAccessTokenCookie(accessToken, response);

        createRefreshTokenCookie(refreshToken, response);
        saveUserRefreshToken(user, refreshToken);
    }

    private void saveUserRefreshToken(User user, String refreshToken) {
        var refreshTokenEntity = RefreshToken.RefreshTokenBuilder
                .aRefreshToken()
                .user(user)
                .expiryDate(Instant.now().plus(2, ChronoUnit.DAYS))
                .refreshTokenValue(refreshToken)
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshTokenEntity);
    }

    public String createAccessToken(User user){

        Instant now = Instant.now();

        String scopes = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("auth-api")
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.MINUTES))
                .subject(user.getName())
                .claim("scope", scopes)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }


    public String createRefreshToken(User user){

        Instant now = Instant.now();

        var claims = JwtClaimsSet.builder()
                .issuer("auth-api")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(user.getName())
                .claim("scope", "REFRESH_TOKEN")
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private void createRefreshTokenCookie(String refreshToken, HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60); // in seconds
        response.addCookie(refreshTokenCookie);
    }

    private void createAccessTokenCookie(String accessToken, HttpServletResponse response) {
        Cookie refreshAccessTokenCookie = new Cookie("access_token", accessToken);
        refreshAccessTokenCookie.setHttpOnly(true);
        refreshAccessTokenCookie.setSecure(true);
        refreshAccessTokenCookie.setMaxAge(15 * 24 * 60 * 60);
        response.addCookie(refreshAccessTokenCookie);
    }


    public String getJwtRefreshTokenFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, "refresh_token");
    }

    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public void generateTokensWithRefreshToken(HttpServletRequest request, HttpServletResponse response) {

        String refreshTokenValue = getJwtRefreshTokenFromCookies(request);
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshTokenValue(refreshTokenValue)
                .orElseThrow(() -> new RuntimeException("Refresh Token Invalid"));

        verifyExpiration(refreshToken);

        User user = refreshToken.getUser();

        refreshTokenRepository.delete(refreshToken);
        generateTokens(user, response);
    }

    public void verifyExpiration(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken
                    .getRefreshTokenValue() + " Refresh token is expired. Please make a new login..!");
        }
    }
}

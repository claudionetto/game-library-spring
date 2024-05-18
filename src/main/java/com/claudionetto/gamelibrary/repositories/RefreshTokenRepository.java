package com.claudionetto.gamelibrary.repositories;

import com.claudionetto.gamelibrary.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshTokenValue(String refreshTokenValue);
}

package com.claudionetto.gamelibrary.models;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "REFRESH_TOKEN", nullable = false, length = 10000)
    private String refreshTokenValue;

    @Column(name = "REVOKED")
    private boolean revoked;

    @Column(name = "EXPIRY_DATE")
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRefreshTokenValue() {
        return refreshTokenValue;
    }

    public void setRefreshTokenValue(String refreshTokenValue) {
        this.refreshTokenValue = refreshTokenValue;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public static final class RefreshTokenBuilder {
        private Long id;
        private String refreshTokenValue;
        private boolean revoked;
        private Instant expiryDate;
        private User user;

        private RefreshTokenBuilder() {
        }

        public static RefreshTokenBuilder aRefreshToken() {
            return new RefreshTokenBuilder();
        }

        public RefreshTokenBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RefreshTokenBuilder refreshTokenValue(String refreshTokenValue) {
            this.refreshTokenValue = refreshTokenValue;
            return this;
        }

        public RefreshTokenBuilder revoked(boolean revoked) {
            this.revoked = revoked;
            return this;
        }

        public RefreshTokenBuilder expiryDate(Instant expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public RefreshTokenBuilder user(User user) {
            this.user = user;
            return this;
        }

        public RefreshToken build() {
            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setId(id);
            refreshToken.setRevoked(revoked);
            refreshToken.setUser(user);
            refreshToken.refreshTokenValue = this.refreshTokenValue;
            refreshToken.expiryDate = this.expiryDate;
            return refreshToken;
        }
    }
}

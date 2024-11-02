package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String accessToken;
    private String refreshToken;
    private java.util.Date tokenExpire;

    public User(String name, String email, String accessToken, String refreshToken, java.util.Date tokenExpire) {
        this.name = name;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenExpire = tokenExpire;
    }

    // Getter und Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public java.util.Date getTokenExpire() {
        return tokenExpire;
    }

    public void setTokenExpire(java.util.Date tokenExpire) {
        this.tokenExpire = tokenExpire;
    }
}

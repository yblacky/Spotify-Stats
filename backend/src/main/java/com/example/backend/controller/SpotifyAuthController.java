package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.service.SpotifyService;

import java.net.URI;

@RestController
public class SpotifyAuthController {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.redirect.uri}")
    private String redirectUri;

    private final SpotifyService spotifyService;

    public SpotifyAuthController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    // 1. Endpunkt für die Benutzeranmeldung
    @GetMapping("/login")
    public ResponseEntity<Void> redirectToSpotifyAuth() {
        String spotifyAuthUrl = "https://accounts.spotify.com/authorize" +
                "?client_id=" + clientId +
                "&response_type=code" +
                "&redirect_uri=" + redirectUri +
                "&scope=user-read-private%20user-read-email";

        URI redirectUri = URI.create(spotifyAuthUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);

        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER); // HTTP 303 für Redirect
    }

    // 2. Endpunkt für den Callback nach der Anmeldung
    @GetMapping("/callback")
    public String handleSpotifyCallback(@RequestParam("code") String code) {
        return spotifyService.exchangeCodeForAccessToken(code);
    }

}

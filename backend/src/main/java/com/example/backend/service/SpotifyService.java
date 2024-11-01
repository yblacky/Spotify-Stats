package com.example.backend.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Service
public class SpotifyService {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Value("${spotify.token.url}")
    private String tokenUrl;

    @Value("${spotify.api.url}")
    private String apiUrl;

    private String accessToken;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.spotify.com/v1")  // Stelle sicher, dass dies korrekt ist
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    // Methode zur Authentifizierung und Token-Aktualisierung
    private void authenticate() {
        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        try {
            WebClient authClient = WebClient.builder()
                    .baseUrl(tokenUrl)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .build();

            String response = authClient.post()
                    .bodyValue("grant_type=client_credentials")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JSONObject json = new JSONObject(response);
            accessToken = json.getString("access_token");
            System.out.println("Generated Access Token: " + accessToken);
        } catch (WebClientResponseException e) {
            System.out.println("Error in authentication: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Beispielmethode, um ein Album von Spotify abzurufen
    public String getAlbum(String albumId) {
        if (accessToken == null) {
            authenticate();
        }

        try {
            return webClient.get()
                    // Explizite URL mit vollständiger URI
                    .uri("https://api.spotify.com/v1/albums/{albumId}", albumId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.out.println("Error fetching album: " + e.getResponseBodyAsString());
            return "Error: " + e.getStatusCode();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}


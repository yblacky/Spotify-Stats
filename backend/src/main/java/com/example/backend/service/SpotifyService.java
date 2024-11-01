package com.example.backend.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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

    @Value("${spotify.redirect.uri}")
    private String redirectUri;

    private String clientAccessToken;
    private String userAccessToken;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.spotify.com/v1")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    // Methode zur Authentifizierung und Token-Aktualisierung für client-seitige Anfragen
    private void authenticateClient() {
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
            clientAccessToken = json.getString("access_token");
            System.out.println("Generated Client Access Token: " + clientAccessToken);
        } catch (WebClientResponseException e) {
            System.out.println("Error in client authentication: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Methode, um den Benutzer-Access-Token auszutauschen
    public String exchangeCodeForAccessToken(String code) {
        String tokenUrl = "https://accounts.spotify.com/api/token";

        WebClient webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();

        String clientIdAndSecret = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(clientIdAndSecret.getBytes());

        try {
            String response = webClient.post()
                    .uri(tokenUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
                    .bodyValue("grant_type=authorization_code&code=" + code + "&redirect_uri=" + redirectUri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JSONObject jsonResponse = new JSONObject(response);
            userAccessToken = jsonResponse.getString("access_token");
            System.out.println("Generated User Access Token: " + userAccessToken);
            return userAccessToken;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error exchanging code for access token";
        }
    }

    // Methode, um ein Album von Spotify abzurufen (client-seitige Authentifizierung)
    public String getAlbum(String albumId) {
        if (clientAccessToken == null) {
            authenticateClient();
        }

        try {
            return webClient.get()
                    .uri("/albums/{albumId}", albumId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + clientAccessToken)
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

    // Methode, um das Profil eines Benutzers abzurufen (benutzerspezifische Authentifizierung)
    public String getUserProfile() {
        if (userAccessToken == null) {
            return "User not authenticated";
        }

        try {
            return webClient.get()
                    .uri("/me")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + userAccessToken)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.out.println("Error fetching user profile: " + e.getResponseBodyAsString());
            return "Error: " + e.getStatusCode();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}

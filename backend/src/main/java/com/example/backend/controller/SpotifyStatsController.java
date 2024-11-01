package com.example.backend.controller;

import com.example.backend.service.SpotifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class SpotifyStatsController {

    private final SpotifyService spotifyService;

    public SpotifyStatsController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/album/{albumId}")
    public String getAlbum(@PathVariable String albumId) {
        return spotifyService.getAlbum(albumId);
    }
}


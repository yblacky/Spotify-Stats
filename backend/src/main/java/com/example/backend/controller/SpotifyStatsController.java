package com.example.backend.controller;

import com.example.backend.service.SpotifyStatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")


public class SpotifyStatsController {

    private final SpotifyStatsService statsService;

    public SpotifyStatsController(SpotifyStatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/user")
    public String getUserStats() {
        return statsService.getUserStats();
    }
}

import { Component, OnInit } from '@angular/core';
import { SpotifyStatsService } from '../spotify-stats.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-stats',
  templateUrl: './user-stats.component.html',
  styleUrls: ['./user-stats.component.scss'],
  standalone: true,
  imports: [CommonModule]  // Hier CommonModule hinzufügen
})
export class UserStatsComponent implements OnInit {
  userStats: any;

  constructor(private spotifyStatsService: SpotifyStatsService) {}

  ngOnInit(): void {
    this.fetchUserStats();
  }

  fetchUserStats(): void {
    this.spotifyStatsService.getUserStats().subscribe(
      (data) => {
        this.userStats = data;
        console.log('User Stats:', data);
      },
      (error) => {
        console.error('Error fetching user stats', error);
      }
    );
  }
}

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SpotifyStatsService } from '../spotify-stats.service';

@Component({
  selector: 'app-user-stats',
  templateUrl: './user-stats.component.html',
  styleUrls: ['./user-stats.component.scss'],
  standalone: true,
  imports: [CommonModule]
})
export class UserStatsComponent implements OnInit {
  userStats: any;

  constructor(private statsService: SpotifyStatsService) {}

  ngOnInit(): void {
    this.statsService.getUserStats().subscribe(data => {
      this.userStats = data;
    });
  }
}

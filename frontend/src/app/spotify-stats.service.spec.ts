import { TestBed } from '@angular/core/testing';

import { SpotifyStatsService } from './spotify-stats.service';

describe('SpotifyStatsService', () => {
  let service: SpotifyStatsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpotifyStatsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

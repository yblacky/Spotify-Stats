import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SpotifyStatsService {

  private apiUrl = 'http://localhost:8080/api/stats/user';

  constructor(private http: HttpClient) {}

  getUserStats(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }
}

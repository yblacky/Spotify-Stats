import { Routes } from '@angular/router';
import { UserStatsComponent } from './user-stats/user-stats.component';


export const appRoutes: Routes = [
  { path: 'user-stats', component: UserStatsComponent },
  { path: '', redirectTo: '/user-stats', pathMatch: 'full' }
];

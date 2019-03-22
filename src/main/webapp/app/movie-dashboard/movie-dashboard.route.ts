import { Routes } from '@angular/router';

import { MovieDashboardComponent } from './movie-dashboard.component';
import { UserRouteAccessService } from 'app/core';

export const movieDashboardRoutes: Routes = [
    {
        path: 'movie-dashboard',
        component: MovieDashboardComponent,
        data: {
            pageTitle: 'Movie Dashboard'
        },
        canActivate: [UserRouteAccessService]
    }
];

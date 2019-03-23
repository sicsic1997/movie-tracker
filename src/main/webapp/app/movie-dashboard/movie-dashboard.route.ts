import { Routes } from '@angular/router';

import { MovieDashboardComponent } from './movie-dashboard.component';
import { UserRouteAccessService } from 'app/core';
import { JhiResolvePagingParams } from 'ng-jhipster';

export const movieDashboardRoutes: Routes = [
    {
        path: 'movie-dashboard',
        component: MovieDashboardComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            pageTitle: 'Movie Dashboard',
            defaultSort: 'id,asc'
        },
        canActivate: [UserRouteAccessService]
    }
];

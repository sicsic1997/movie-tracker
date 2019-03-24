import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { MovieDashboardComponent } from './movie-dashboard.component';
import { UserRouteAccessService } from 'app/core';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { MovieDashboardDetailComponent } from 'app/movie-dashboard/movie-dashboard-detail.component';
import { Injectable } from '@angular/core';
import { IMovie, Movie } from 'app/shared/model/movie.model';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { MovieDashboardService } from 'app/movie-dashboard/movie-dashboard.service';

@Injectable({ providedIn: 'root' })
export class MovieDashboardResolve implements Resolve<IMovie> {
    constructor(private service: MovieDashboardService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMovie> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Movie>) => response.ok),
                map((movie: HttpResponse<Movie>) => movie.body)
            );
        }
        return of(new Movie());
    }
}

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
    },
    {
        path: 'movie-dashboard/:id',
        component: MovieDashboardDetailComponent,
        resolve: {
            movie: MovieDashboardResolve
        },
        data: {
            pageTitle: 'Movie Detail'
        },
        canActivate: [UserRouteAccessService]
    }
];

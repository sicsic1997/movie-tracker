import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MovieStatus } from 'app/shared/model/movie-status.model';
import { MovieStatusService } from './movie-status.service';
import { MovieStatusComponent } from './movie-status.component';
import { MovieStatusDetailComponent } from './movie-status-detail.component';
import { MovieStatusUpdateComponent } from './movie-status-update.component';
import { MovieStatusDeletePopupComponent } from './movie-status-delete-dialog.component';
import { IMovieStatus } from 'app/shared/model/movie-status.model';

@Injectable({ providedIn: 'root' })
export class MovieStatusResolve implements Resolve<IMovieStatus> {
    constructor(private service: MovieStatusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMovieStatus> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MovieStatus>) => response.ok),
                map((movieStatus: HttpResponse<MovieStatus>) => movieStatus.body)
            );
        }
        return of(new MovieStatus());
    }
}

export const movieStatusRoute: Routes = [
    {
        path: '',
        component: MovieStatusComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'MovieStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MovieStatusDetailComponent,
        resolve: {
            movieStatus: MovieStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MovieStatusUpdateComponent,
        resolve: {
            movieStatus: MovieStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MovieStatusUpdateComponent,
        resolve: {
            movieStatus: MovieStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const movieStatusPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MovieStatusDeletePopupComponent,
        resolve: {
            movieStatus: MovieStatusResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

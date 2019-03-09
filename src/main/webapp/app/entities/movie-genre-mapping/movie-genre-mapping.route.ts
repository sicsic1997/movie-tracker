import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MovieGenreMapping } from 'app/shared/model/movie-genre-mapping.model';
import { MovieGenreMappingService } from './movie-genre-mapping.service';
import { MovieGenreMappingComponent } from './movie-genre-mapping.component';
import { MovieGenreMappingDetailComponent } from './movie-genre-mapping-detail.component';
import { MovieGenreMappingUpdateComponent } from './movie-genre-mapping-update.component';
import { MovieGenreMappingDeletePopupComponent } from './movie-genre-mapping-delete-dialog.component';
import { IMovieGenreMapping } from 'app/shared/model/movie-genre-mapping.model';

@Injectable({ providedIn: 'root' })
export class MovieGenreMappingResolve implements Resolve<IMovieGenreMapping> {
    constructor(private service: MovieGenreMappingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMovieGenreMapping> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MovieGenreMapping>) => response.ok),
                map((movieGenreMapping: HttpResponse<MovieGenreMapping>) => movieGenreMapping.body)
            );
        }
        return of(new MovieGenreMapping());
    }
}

export const movieGenreMappingRoute: Routes = [
    {
        path: '',
        component: MovieGenreMappingComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'MovieGenreMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MovieGenreMappingDetailComponent,
        resolve: {
            movieGenreMapping: MovieGenreMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieGenreMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MovieGenreMappingUpdateComponent,
        resolve: {
            movieGenreMapping: MovieGenreMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieGenreMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MovieGenreMappingUpdateComponent,
        resolve: {
            movieGenreMapping: MovieGenreMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieGenreMappings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const movieGenreMappingPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MovieGenreMappingDeletePopupComponent,
        resolve: {
            movieGenreMapping: MovieGenreMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieGenreMappings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

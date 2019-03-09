import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MovieLanguageMapping } from 'app/shared/model/movie-language-mapping.model';
import { MovieLanguageMappingService } from './movie-language-mapping.service';
import { MovieLanguageMappingComponent } from './movie-language-mapping.component';
import { MovieLanguageMappingDetailComponent } from './movie-language-mapping-detail.component';
import { MovieLanguageMappingUpdateComponent } from './movie-language-mapping-update.component';
import { MovieLanguageMappingDeletePopupComponent } from './movie-language-mapping-delete-dialog.component';
import { IMovieLanguageMapping } from 'app/shared/model/movie-language-mapping.model';

@Injectable({ providedIn: 'root' })
export class MovieLanguageMappingResolve implements Resolve<IMovieLanguageMapping> {
    constructor(private service: MovieLanguageMappingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMovieLanguageMapping> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MovieLanguageMapping>) => response.ok),
                map((movieLanguageMapping: HttpResponse<MovieLanguageMapping>) => movieLanguageMapping.body)
            );
        }
        return of(new MovieLanguageMapping());
    }
}

export const movieLanguageMappingRoute: Routes = [
    {
        path: '',
        component: MovieLanguageMappingComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'MovieLanguageMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MovieLanguageMappingDetailComponent,
        resolve: {
            movieLanguageMapping: MovieLanguageMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieLanguageMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MovieLanguageMappingUpdateComponent,
        resolve: {
            movieLanguageMapping: MovieLanguageMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieLanguageMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MovieLanguageMappingUpdateComponent,
        resolve: {
            movieLanguageMapping: MovieLanguageMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieLanguageMappings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const movieLanguageMappingPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MovieLanguageMappingDeletePopupComponent,
        resolve: {
            movieLanguageMapping: MovieLanguageMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MovieLanguageMappings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

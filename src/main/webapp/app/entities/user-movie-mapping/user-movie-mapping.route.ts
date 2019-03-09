import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserMovieMapping } from 'app/shared/model/user-movie-mapping.model';
import { UserMovieMappingService } from './user-movie-mapping.service';
import { UserMovieMappingComponent } from './user-movie-mapping.component';
import { UserMovieMappingDetailComponent } from './user-movie-mapping-detail.component';
import { UserMovieMappingUpdateComponent } from './user-movie-mapping-update.component';
import { UserMovieMappingDeletePopupComponent } from './user-movie-mapping-delete-dialog.component';
import { IUserMovieMapping } from 'app/shared/model/user-movie-mapping.model';

@Injectable({ providedIn: 'root' })
export class UserMovieMappingResolve implements Resolve<IUserMovieMapping> {
    constructor(private service: UserMovieMappingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserMovieMapping> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<UserMovieMapping>) => response.ok),
                map((userMovieMapping: HttpResponse<UserMovieMapping>) => userMovieMapping.body)
            );
        }
        return of(new UserMovieMapping());
    }
}

export const userMovieMappingRoute: Routes = [
    {
        path: '',
        component: UserMovieMappingComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'UserMovieMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: UserMovieMappingDetailComponent,
        resolve: {
            userMovieMapping: UserMovieMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserMovieMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: UserMovieMappingUpdateComponent,
        resolve: {
            userMovieMapping: UserMovieMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserMovieMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: UserMovieMappingUpdateComponent,
        resolve: {
            userMovieMapping: UserMovieMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserMovieMappings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userMovieMappingPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: UserMovieMappingDeletePopupComponent,
        resolve: {
            userMovieMapping: UserMovieMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'UserMovieMappings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

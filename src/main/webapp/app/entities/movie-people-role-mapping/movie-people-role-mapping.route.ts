import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MoviePeopleRoleMapping } from 'app/shared/model/movie-people-role-mapping.model';
import { MoviePeopleRoleMappingService } from './movie-people-role-mapping.service';
import { MoviePeopleRoleMappingComponent } from './movie-people-role-mapping.component';
import { MoviePeopleRoleMappingDetailComponent } from './movie-people-role-mapping-detail.component';
import { MoviePeopleRoleMappingUpdateComponent } from './movie-people-role-mapping-update.component';
import { MoviePeopleRoleMappingDeletePopupComponent } from './movie-people-role-mapping-delete-dialog.component';
import { IMoviePeopleRoleMapping } from 'app/shared/model/movie-people-role-mapping.model';

@Injectable({ providedIn: 'root' })
export class MoviePeopleRoleMappingResolve implements Resolve<IMoviePeopleRoleMapping> {
    constructor(private service: MoviePeopleRoleMappingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMoviePeopleRoleMapping> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MoviePeopleRoleMapping>) => response.ok),
                map((moviePeopleRoleMapping: HttpResponse<MoviePeopleRoleMapping>) => moviePeopleRoleMapping.body)
            );
        }
        return of(new MoviePeopleRoleMapping());
    }
}

export const moviePeopleRoleMappingRoute: Routes = [
    {
        path: '',
        component: MoviePeopleRoleMappingComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'MoviePeopleRoleMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MoviePeopleRoleMappingDetailComponent,
        resolve: {
            moviePeopleRoleMapping: MoviePeopleRoleMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MoviePeopleRoleMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MoviePeopleRoleMappingUpdateComponent,
        resolve: {
            moviePeopleRoleMapping: MoviePeopleRoleMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MoviePeopleRoleMappings'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MoviePeopleRoleMappingUpdateComponent,
        resolve: {
            moviePeopleRoleMapping: MoviePeopleRoleMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MoviePeopleRoleMappings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const moviePeopleRoleMappingPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MoviePeopleRoleMappingDeletePopupComponent,
        resolve: {
            moviePeopleRoleMapping: MoviePeopleRoleMappingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MoviePeopleRoleMappings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

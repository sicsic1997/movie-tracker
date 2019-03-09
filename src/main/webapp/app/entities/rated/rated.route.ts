import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Rated } from 'app/shared/model/rated.model';
import { RatedService } from './rated.service';
import { RatedComponent } from './rated.component';
import { RatedDetailComponent } from './rated-detail.component';
import { RatedUpdateComponent } from './rated-update.component';
import { RatedDeletePopupComponent } from './rated-delete-dialog.component';
import { IRated } from 'app/shared/model/rated.model';

@Injectable({ providedIn: 'root' })
export class RatedResolve implements Resolve<IRated> {
    constructor(private service: RatedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRated> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Rated>) => response.ok),
                map((rated: HttpResponse<Rated>) => rated.body)
            );
        }
        return of(new Rated());
    }
}

export const ratedRoute: Routes = [
    {
        path: '',
        component: RatedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Rateds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RatedDetailComponent,
        resolve: {
            rated: RatedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rateds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RatedUpdateComponent,
        resolve: {
            rated: RatedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rateds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RatedUpdateComponent,
        resolve: {
            rated: RatedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rateds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ratedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RatedDeletePopupComponent,
        resolve: {
            rated: RatedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Rateds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

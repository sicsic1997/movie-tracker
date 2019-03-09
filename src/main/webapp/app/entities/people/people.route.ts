import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { People } from 'app/shared/model/people.model';
import { PeopleService } from './people.service';
import { PeopleComponent } from './people.component';
import { PeopleDetailComponent } from './people-detail.component';
import { PeopleUpdateComponent } from './people-update.component';
import { PeopleDeletePopupComponent } from './people-delete-dialog.component';
import { IPeople } from 'app/shared/model/people.model';

@Injectable({ providedIn: 'root' })
export class PeopleResolve implements Resolve<IPeople> {
    constructor(private service: PeopleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPeople> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<People>) => response.ok),
                map((people: HttpResponse<People>) => people.body)
            );
        }
        return of(new People());
    }
}

export const peopleRoute: Routes = [
    {
        path: '',
        component: PeopleComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'People'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PeopleDetailComponent,
        resolve: {
            people: PeopleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'People'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PeopleUpdateComponent,
        resolve: {
            people: PeopleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'People'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PeopleUpdateComponent,
        resolve: {
            people: PeopleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'People'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const peoplePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PeopleDeletePopupComponent,
        resolve: {
            people: PeopleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'People'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

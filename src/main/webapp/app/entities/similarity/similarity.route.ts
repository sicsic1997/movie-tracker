import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Similarity } from 'app/shared/model/similarity.model';
import { SimilarityService } from './similarity.service';
import { SimilarityComponent } from './similarity.component';
import { SimilarityDetailComponent } from './similarity-detail.component';
import { SimilarityUpdateComponent } from './similarity-update.component';
import { SimilarityDeletePopupComponent } from './similarity-delete-dialog.component';
import { ISimilarity } from 'app/shared/model/similarity.model';

@Injectable({ providedIn: 'root' })
export class SimilarityResolve implements Resolve<ISimilarity> {
    constructor(private service: SimilarityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISimilarity> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Similarity>) => response.ok),
                map((similarity: HttpResponse<Similarity>) => similarity.body)
            );
        }
        return of(new Similarity());
    }
}

export const similarityRoute: Routes = [
    {
        path: '',
        component: SimilarityComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Similarities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SimilarityDetailComponent,
        resolve: {
            similarity: SimilarityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Similarities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SimilarityUpdateComponent,
        resolve: {
            similarity: SimilarityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Similarities'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SimilarityUpdateComponent,
        resolve: {
            similarity: SimilarityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Similarities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const similarityPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SimilarityDeletePopupComponent,
        resolve: {
            similarity: SimilarityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Similarities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

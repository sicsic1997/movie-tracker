import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserDashboardComponent } from './user-dashboard.component';
import { UserRouteAccessService } from 'app/core';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Injectable } from '@angular/core';
import { IMovie, Movie } from 'app/shared/model/movie.model';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { UserDashboardService } from 'app/user-dashboard/user-dashboard.service';

export const UserDashboardRoutes: Routes = [
    {
        path: 'user-dashboard',
        component: UserDashboardComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            pageTitle: 'User Dashboard',
            defaultSort: 'id,asc'
        },
        canActivate: [UserRouteAccessService]
    }
];

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { IMovie } from 'app/shared/model/movie.model';
import { AccountService } from 'app/core';
import { ITEMS_PER_PAGE } from 'app/shared';
import { UserDashboardService } from './user-dashboard.service';
import { IUserMovieMapping } from 'app/shared/model/user-movie-mapping.model';

@Component({
    selector: 'jhi-movie',
    templateUrl: './user-dashboard.component.html',
    styleUrls: ['user-dashboard.css']
})
export class UserDashboardComponent implements OnInit, OnDestroy {
    currentAccount: any;
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    isAuthenticated: boolean;
    moviesHistory: IMovie[];
    moviesWishlist: IMovie[];
    moviesSuggestions: IMovie[];

    constructor(
        protected userDashboardService: UserDashboardService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        this.userDashboardService
            .findByLoginInHistory()
            .subscribe(
                (res: HttpResponse<IUserMovieMapping[]>) => (this.moviesHistory = res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.userDashboardService
            .findByLoginInWishlist()
            .subscribe(
                (res: HttpResponse<IUserMovieMapping[]>) => (this.moviesWishlist = res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.userDashboardService
            .querySuggestions()
            .subscribe(
                (res: HttpResponse<IUserMovieMapping[]>) => (this.moviesSuggestions = res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/user-dashboard'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/user-dashboard',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.registerChangeInMovies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMovie) {
        return item.id;
    }

    registerChangeInMovies() {
        this.eventSubscriber = this.eventManager.subscribe('movieListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

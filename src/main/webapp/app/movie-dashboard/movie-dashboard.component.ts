import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { IMovie } from 'app/shared/model/movie.model';
import { AccountService } from 'app/core';
import { ITEMS_PER_PAGE } from 'app/shared';
import { MovieDashboardService } from './movie-dashboard.service';
import { IUserMovieMapping } from 'app/shared/model/user-movie-mapping.model';
import { UserMovieMappingService } from 'app/entities/user-movie-mapping';

@Component({
    selector: 'jhi-movie',
    templateUrl: './movie-dashboard.component.html',
    styleUrls: ['movie-dashboard.css']
})
export class MovieDashboardComponent implements OnInit, OnDestroy {
    currentAccount: any;
    movies: IMovie[];
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
    stringFilter: string;
    isAuthenticated: boolean;
    userMovieMapping: IUserMovieMapping[];

    constructor(
        protected movieDashboardService: MovieDashboardService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected userMovieMappingService: UserMovieMappingService
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
        this.movieDashboardService
            .query({
                'filter.contains': this.stringFilter ? this.stringFilter : '',
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IMovie[]>) => this.paginateMovies(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.isAuthenticated = this.accountService.isAuthenticated();
        if (this.isAuthenticated) {
            this.loadUserMovieData();
        }
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/movie-dashboard'], {
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
            '/movie',
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

    filterItems(value) {
        this.stringFilter = value;
        this.loadAll();
    }

    protected paginateMovies(data: IMovie[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.movies = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    loadUserMovieData() {
        this.userMovieMappingService
            .findByLogin()
            .subscribe(
                (res: HttpResponse<IUserMovieMapping[]>) => (this.userMovieMapping = res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    inWishlist(movieId: number) {
        if (this.userMovieMapping == undefined) {
            return false;
        }
        console.log(this.userMovieMapping.length);
        for (let i = 0; i < this.userMovieMapping.length; i++) {
            if (this.userMovieMapping[i].movieId === movieId && this.userMovieMapping[i].movieStatusCode === 'WISH_LIST') {
                return true;
            }
        }
        return false;
    }

    inHistory(movieId: number) {
        if (this.userMovieMapping == undefined) {
            return false;
        }
        for (let i = 0; i < this.userMovieMapping.length; i++) {
            if (this.userMovieMapping[i].movieId === movieId && this.userMovieMapping[i].movieStatusCode === 'HISTORY') {
                return true;
            }
        }
        return false;
    }

    clickWishilist(movieId: number) {
        if (this.inWishlist(movieId)) {
            this.userMovieMappingService
                .deleteByMovieId(movieId, 'WISH_LIST')
                .subscribe((res: HttpResponse<any>) => this.loadUserMovieData(), (res: HttpErrorResponse) => this.onError(res.message));
        } else {
            this.userMovieMappingService
                .createByMovieIdAndStatusCode(movieId, 'WISH_LIST')
                .subscribe((res: HttpResponse<any>) => this.loadUserMovieData(), (res: HttpErrorResponse) => this.onError(res.message));
        }
    }

    clickHistory(movieId: number) {
        if (this.inHistory(movieId)) {
            this.userMovieMappingService
                .deleteByMovieId(movieId, 'HISTORY')
                .subscribe((res: HttpResponse<any>) => this.loadUserMovieData(), (res: HttpErrorResponse) => this.onError(res.message));
        } else {
            this.userMovieMappingService
                .createByMovieIdAndStatusCode(movieId, 'HISTORY')
                .subscribe((res: HttpResponse<any>) => this.loadUserMovieData(), (res: HttpErrorResponse) => this.onError(res.message));
        }
    }
}

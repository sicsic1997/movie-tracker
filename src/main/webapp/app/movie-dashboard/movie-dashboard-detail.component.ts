import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IMovie } from 'app/shared/model/movie.model';
import { MovieDashboardService } from 'app/movie-dashboard/movie-dashboard.service';
import { JhiAlertService } from 'ng-jhipster';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { UserMovieMappingService } from 'app/entities/user-movie-mapping';
import { AccountService, IUser } from 'app/core';
import { IUserMovieMapping } from 'app/shared/model/user-movie-mapping.model';

@Component({
    selector: 'jhi-movie-detail',
    templateUrl: './movie-dashboard-detail.component.html',
    styleUrls: ['movie-dashboard-detail.css']
})
export class MovieDashboardDetailComponent implements OnInit {
    movie: IMovie;
    suggestions: IMovie[];
    isAuthenticated: boolean;
    userMovieMapping: IUserMovieMapping[];

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected movieDashboardService: MovieDashboardService,
        protected jhiAlertService: JhiAlertService,
        protected userMovieMappingService: UserMovieMappingService,
        protected accountService: AccountService
    ) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ movie }) => {
            this.movie = movie;
            this.movieDashboardService
                .querySuggestions(this.movie.id)
                .subscribe(
                    (res: HttpResponse<IMovie[]>) => (this.suggestions = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        });
        this.isAuthenticated = this.accountService.isAuthenticated();
        if (this.isAuthenticated) {
            this.loadUserMovieData();
        }
    }

    loadUserMovieData() {
        this.userMovieMappingService
            .findByMovieAndLogin(this.movie.id)
            .subscribe(
                (res: HttpResponse<IUserMovieMapping[]>) => (this.userMovieMapping = res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
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

    click(movieId: number) {
        console.log('Click' + movieId);
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

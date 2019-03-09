import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUserMovieMapping } from 'app/shared/model/user-movie-mapping.model';
import { UserMovieMappingService } from './user-movie-mapping.service';
import { IUser, UserService } from 'app/core';
import { IMovie } from 'app/shared/model/movie.model';
import { MovieService } from 'app/entities/movie';
import { IMovieStatus } from 'app/shared/model/movie-status.model';
import { MovieStatusService } from 'app/entities/movie-status';

@Component({
    selector: 'jhi-user-movie-mapping-update',
    templateUrl: './user-movie-mapping-update.component.html'
})
export class UserMovieMappingUpdateComponent implements OnInit {
    userMovieMapping: IUserMovieMapping;
    isSaving: boolean;

    users: IUser[];

    movies: IMovie[];

    moviestatuses: IMovieStatus[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected userMovieMappingService: UserMovieMappingService,
        protected userService: UserService,
        protected movieService: MovieService,
        protected movieStatusService: MovieStatusService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userMovieMapping }) => {
            this.userMovieMapping = userMovieMapping;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.movieService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMovie[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMovie[]>) => response.body)
            )
            .subscribe((res: IMovie[]) => (this.movies = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.movieStatusService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMovieStatus[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMovieStatus[]>) => response.body)
            )
            .subscribe((res: IMovieStatus[]) => (this.moviestatuses = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userMovieMapping.id !== undefined) {
            this.subscribeToSaveResponse(this.userMovieMappingService.update(this.userMovieMapping));
        } else {
            this.subscribeToSaveResponse(this.userMovieMappingService.create(this.userMovieMapping));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserMovieMapping>>) {
        result.subscribe((res: HttpResponse<IUserMovieMapping>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackMovieById(index: number, item: IMovie) {
        return item.id;
    }

    trackMovieStatusById(index: number, item: IMovieStatus) {
        return item.id;
    }
}

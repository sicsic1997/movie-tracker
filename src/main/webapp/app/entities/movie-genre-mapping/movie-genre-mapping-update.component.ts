import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMovieGenreMapping } from 'app/shared/model/movie-genre-mapping.model';
import { MovieGenreMappingService } from './movie-genre-mapping.service';
import { IGenre } from 'app/shared/model/genre.model';
import { GenreService } from 'app/entities/genre';
import { IMovie } from 'app/shared/model/movie.model';
import { MovieService } from 'app/entities/movie';

@Component({
    selector: 'jhi-movie-genre-mapping-update',
    templateUrl: './movie-genre-mapping-update.component.html'
})
export class MovieGenreMappingUpdateComponent implements OnInit {
    movieGenreMapping: IMovieGenreMapping;
    isSaving: boolean;

    genres: IGenre[];

    movies: IMovie[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected movieGenreMappingService: MovieGenreMappingService,
        protected genreService: GenreService,
        protected movieService: MovieService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ movieGenreMapping }) => {
            this.movieGenreMapping = movieGenreMapping;
        });
        this.genreService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IGenre[]>) => mayBeOk.ok),
                map((response: HttpResponse<IGenre[]>) => response.body)
            )
            .subscribe((res: IGenre[]) => (this.genres = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.movieService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMovie[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMovie[]>) => response.body)
            )
            .subscribe((res: IMovie[]) => (this.movies = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.movieGenreMapping.id !== undefined) {
            this.subscribeToSaveResponse(this.movieGenreMappingService.update(this.movieGenreMapping));
        } else {
            this.subscribeToSaveResponse(this.movieGenreMappingService.create(this.movieGenreMapping));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovieGenreMapping>>) {
        result.subscribe((res: HttpResponse<IMovieGenreMapping>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGenreById(index: number, item: IGenre) {
        return item.id;
    }

    trackMovieById(index: number, item: IMovie) {
        return item.id;
    }
}

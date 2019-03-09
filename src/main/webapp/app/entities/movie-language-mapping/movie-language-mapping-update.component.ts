import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMovieLanguageMapping } from 'app/shared/model/movie-language-mapping.model';
import { MovieLanguageMappingService } from './movie-language-mapping.service';
import { IMovie } from 'app/shared/model/movie.model';
import { MovieService } from 'app/entities/movie';
import { ILanguage } from 'app/shared/model/language.model';
import { LanguageService } from 'app/entities/language';

@Component({
    selector: 'jhi-movie-language-mapping-update',
    templateUrl: './movie-language-mapping-update.component.html'
})
export class MovieLanguageMappingUpdateComponent implements OnInit {
    movieLanguageMapping: IMovieLanguageMapping;
    isSaving: boolean;

    movies: IMovie[];

    languages: ILanguage[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected movieLanguageMappingService: MovieLanguageMappingService,
        protected movieService: MovieService,
        protected languageService: LanguageService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ movieLanguageMapping }) => {
            this.movieLanguageMapping = movieLanguageMapping;
        });
        this.movieService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMovie[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMovie[]>) => response.body)
            )
            .subscribe((res: IMovie[]) => (this.movies = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.languageService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ILanguage[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILanguage[]>) => response.body)
            )
            .subscribe((res: ILanguage[]) => (this.languages = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.movieLanguageMapping.id !== undefined) {
            this.subscribeToSaveResponse(this.movieLanguageMappingService.update(this.movieLanguageMapping));
        } else {
            this.subscribeToSaveResponse(this.movieLanguageMappingService.create(this.movieLanguageMapping));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovieLanguageMapping>>) {
        result.subscribe(
            (res: HttpResponse<IMovieLanguageMapping>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackMovieById(index: number, item: IMovie) {
        return item.id;
    }

    trackLanguageById(index: number, item: ILanguage) {
        return item.id;
    }
}

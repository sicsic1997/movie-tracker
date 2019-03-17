import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISimilarity } from 'app/shared/model/similarity.model';
import { SimilarityService } from './similarity.service';
import { IMovie } from 'app/shared/model/movie.model';
import { MovieService } from 'app/entities/movie';

@Component({
    selector: 'jhi-similarity-update',
    templateUrl: './similarity-update.component.html'
})
export class SimilarityUpdateComponent implements OnInit {
    similarity: ISimilarity;
    isSaving: boolean;

    movies: IMovie[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected similarityService: SimilarityService,
        protected movieService: MovieService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ similarity }) => {
            this.similarity = similarity;
        });
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
        if (this.similarity.id !== undefined) {
            this.subscribeToSaveResponse(this.similarityService.update(this.similarity));
        } else {
            this.subscribeToSaveResponse(this.similarityService.create(this.similarity));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISimilarity>>) {
        result.subscribe((res: HttpResponse<ISimilarity>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}

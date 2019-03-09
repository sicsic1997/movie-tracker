import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IMovieStatus } from 'app/shared/model/movie-status.model';
import { MovieStatusService } from './movie-status.service';

@Component({
    selector: 'jhi-movie-status-update',
    templateUrl: './movie-status-update.component.html'
})
export class MovieStatusUpdateComponent implements OnInit {
    movieStatus: IMovieStatus;
    isSaving: boolean;

    constructor(protected movieStatusService: MovieStatusService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ movieStatus }) => {
            this.movieStatus = movieStatus;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.movieStatus.id !== undefined) {
            this.subscribeToSaveResponse(this.movieStatusService.update(this.movieStatus));
        } else {
            this.subscribeToSaveResponse(this.movieStatusService.create(this.movieStatus));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovieStatus>>) {
        result.subscribe((res: HttpResponse<IMovieStatus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

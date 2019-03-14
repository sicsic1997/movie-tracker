import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMovie } from 'app/shared/model/movie.model';
import { MovieService } from './movie.service';
import { IRated } from 'app/shared/model/rated.model';
import { RatedService } from 'app/entities/rated';
import { IProduction } from 'app/shared/model/production.model';
import { ProductionService } from 'app/entities/production';

@Component({
    selector: 'jhi-movie-update',
    templateUrl: './movie-update.component.html'
})
export class MovieUpdateComponent implements OnInit {
    movie: IMovie;
    isSaving: boolean;

    rateds: IRated[];

    productions: IProduction[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected movieService: MovieService,
        protected ratedService: RatedService,
        protected productionService: ProductionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ movie }) => {
            this.movie = movie;
        });
        this.ratedService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRated[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRated[]>) => response.body)
            )
            .subscribe((res: IRated[]) => (this.rateds = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProduction[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduction[]>) => response.body)
            )
            .subscribe((res: IProduction[]) => (this.productions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.movie.id !== undefined) {
            this.subscribeToSaveResponse(this.movieService.update(this.movie));
        } else {
            this.subscribeToSaveResponse(this.movieService.create(this.movie));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMovie>>) {
        result.subscribe((res: HttpResponse<IMovie>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRatedById(index: number, item: IRated) {
        return item.id;
    }

    trackProductionById(index: number, item: IProduction) {
        return item.id;
    }
}

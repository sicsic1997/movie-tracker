import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IRated } from 'app/shared/model/rated.model';
import { RatedService } from './rated.service';

@Component({
    selector: 'jhi-rated-update',
    templateUrl: './rated-update.component.html'
})
export class RatedUpdateComponent implements OnInit {
    rated: IRated;
    isSaving: boolean;

    constructor(protected ratedService: RatedService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rated }) => {
            this.rated = rated;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.rated.id !== undefined) {
            this.subscribeToSaveResponse(this.ratedService.update(this.rated));
        } else {
            this.subscribeToSaveResponse(this.ratedService.create(this.rated));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRated>>) {
        result.subscribe((res: HttpResponse<IRated>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

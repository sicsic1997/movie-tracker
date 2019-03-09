import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IProduction } from 'app/shared/model/production.model';
import { ProductionService } from './production.service';

@Component({
    selector: 'jhi-production-update',
    templateUrl: './production-update.component.html'
})
export class ProductionUpdateComponent implements OnInit {
    production: IProduction;
    isSaving: boolean;

    constructor(protected productionService: ProductionService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ production }) => {
            this.production = production;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.production.id !== undefined) {
            this.subscribeToSaveResponse(this.productionService.update(this.production));
        } else {
            this.subscribeToSaveResponse(this.productionService.create(this.production));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduction>>) {
        result.subscribe((res: HttpResponse<IProduction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

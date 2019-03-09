import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from './people.service';

@Component({
    selector: 'jhi-people-update',
    templateUrl: './people-update.component.html'
})
export class PeopleUpdateComponent implements OnInit {
    people: IPeople;
    isSaving: boolean;

    constructor(protected peopleService: PeopleService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ people }) => {
            this.people = people;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.people.id !== undefined) {
            this.subscribeToSaveResponse(this.peopleService.update(this.people));
        } else {
            this.subscribeToSaveResponse(this.peopleService.create(this.people));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeople>>) {
        result.subscribe((res: HttpResponse<IPeople>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

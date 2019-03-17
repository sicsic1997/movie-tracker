import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IWord } from 'app/shared/model/word.model';
import { WordService } from './word.service';

@Component({
    selector: 'jhi-word-update',
    templateUrl: './word-update.component.html'
})
export class WordUpdateComponent implements OnInit {
    word: IWord;
    isSaving: boolean;

    constructor(protected wordService: WordService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ word }) => {
            this.word = word;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.word.id !== undefined) {
            this.subscribeToSaveResponse(this.wordService.update(this.word));
        } else {
            this.subscribeToSaveResponse(this.wordService.create(this.word));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IWord>>) {
        result.subscribe((res: HttpResponse<IWord>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}

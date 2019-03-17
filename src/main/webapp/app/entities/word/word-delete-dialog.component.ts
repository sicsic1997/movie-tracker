import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWord } from 'app/shared/model/word.model';
import { WordService } from './word.service';

@Component({
    selector: 'jhi-word-delete-dialog',
    templateUrl: './word-delete-dialog.component.html'
})
export class WordDeleteDialogComponent {
    word: IWord;

    constructor(protected wordService: WordService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wordService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'wordListModification',
                content: 'Deleted an word'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-word-delete-popup',
    template: ''
})
export class WordDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ word }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WordDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.word = word;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/word', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/word', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

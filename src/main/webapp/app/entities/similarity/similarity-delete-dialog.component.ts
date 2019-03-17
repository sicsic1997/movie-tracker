import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISimilarity } from 'app/shared/model/similarity.model';
import { SimilarityService } from './similarity.service';

@Component({
    selector: 'jhi-similarity-delete-dialog',
    templateUrl: './similarity-delete-dialog.component.html'
})
export class SimilarityDeleteDialogComponent {
    similarity: ISimilarity;

    constructor(
        protected similarityService: SimilarityService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.similarityService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'similarityListModification',
                content: 'Deleted an similarity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-similarity-delete-popup',
    template: ''
})
export class SimilarityDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ similarity }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SimilarityDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.similarity = similarity;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/similarity', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/similarity', { outlets: { popup: null } }]);
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

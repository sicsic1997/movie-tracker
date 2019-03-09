import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRated } from 'app/shared/model/rated.model';
import { RatedService } from './rated.service';

@Component({
    selector: 'jhi-rated-delete-dialog',
    templateUrl: './rated-delete-dialog.component.html'
})
export class RatedDeleteDialogComponent {
    rated: IRated;

    constructor(protected ratedService: RatedService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ratedService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ratedListModification',
                content: 'Deleted an rated'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rated-delete-popup',
    template: ''
})
export class RatedDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rated }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RatedDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.rated = rated;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/rated', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/rated', { outlets: { popup: null } }]);
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

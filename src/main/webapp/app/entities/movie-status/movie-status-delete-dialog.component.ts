import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMovieStatus } from 'app/shared/model/movie-status.model';
import { MovieStatusService } from './movie-status.service';

@Component({
    selector: 'jhi-movie-status-delete-dialog',
    templateUrl: './movie-status-delete-dialog.component.html'
})
export class MovieStatusDeleteDialogComponent {
    movieStatus: IMovieStatus;

    constructor(
        protected movieStatusService: MovieStatusService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.movieStatusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'movieStatusListModification',
                content: 'Deleted an movieStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-movie-status-delete-popup',
    template: ''
})
export class MovieStatusDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ movieStatus }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MovieStatusDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.movieStatus = movieStatus;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/movie-status', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/movie-status', { outlets: { popup: null } }]);
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

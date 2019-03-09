import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserMovieMapping } from 'app/shared/model/user-movie-mapping.model';
import { UserMovieMappingService } from './user-movie-mapping.service';

@Component({
    selector: 'jhi-user-movie-mapping-delete-dialog',
    templateUrl: './user-movie-mapping-delete-dialog.component.html'
})
export class UserMovieMappingDeleteDialogComponent {
    userMovieMapping: IUserMovieMapping;

    constructor(
        protected userMovieMappingService: UserMovieMappingService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userMovieMappingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'userMovieMappingListModification',
                content: 'Deleted an userMovieMapping'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-movie-mapping-delete-popup',
    template: ''
})
export class UserMovieMappingDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userMovieMapping }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(UserMovieMappingDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.userMovieMapping = userMovieMapping;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/user-movie-mapping', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/user-movie-mapping', { outlets: { popup: null } }]);
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

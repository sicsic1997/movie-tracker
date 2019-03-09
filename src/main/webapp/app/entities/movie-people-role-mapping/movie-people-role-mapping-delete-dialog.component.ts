import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMoviePeopleRoleMapping } from 'app/shared/model/movie-people-role-mapping.model';
import { MoviePeopleRoleMappingService } from './movie-people-role-mapping.service';

@Component({
    selector: 'jhi-movie-people-role-mapping-delete-dialog',
    templateUrl: './movie-people-role-mapping-delete-dialog.component.html'
})
export class MoviePeopleRoleMappingDeleteDialogComponent {
    moviePeopleRoleMapping: IMoviePeopleRoleMapping;

    constructor(
        protected moviePeopleRoleMappingService: MoviePeopleRoleMappingService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.moviePeopleRoleMappingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'moviePeopleRoleMappingListModification',
                content: 'Deleted an moviePeopleRoleMapping'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-movie-people-role-mapping-delete-popup',
    template: ''
})
export class MoviePeopleRoleMappingDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ moviePeopleRoleMapping }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MoviePeopleRoleMappingDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.moviePeopleRoleMapping = moviePeopleRoleMapping;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/movie-people-role-mapping', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/movie-people-role-mapping', { outlets: { popup: null } }]);
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

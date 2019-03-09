import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMovieGenreMapping } from 'app/shared/model/movie-genre-mapping.model';
import { MovieGenreMappingService } from './movie-genre-mapping.service';

@Component({
    selector: 'jhi-movie-genre-mapping-delete-dialog',
    templateUrl: './movie-genre-mapping-delete-dialog.component.html'
})
export class MovieGenreMappingDeleteDialogComponent {
    movieGenreMapping: IMovieGenreMapping;

    constructor(
        protected movieGenreMappingService: MovieGenreMappingService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.movieGenreMappingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'movieGenreMappingListModification',
                content: 'Deleted an movieGenreMapping'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-movie-genre-mapping-delete-popup',
    template: ''
})
export class MovieGenreMappingDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ movieGenreMapping }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MovieGenreMappingDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.movieGenreMapping = movieGenreMapping;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/movie-genre-mapping', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/movie-genre-mapping', { outlets: { popup: null } }]);
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

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMovieLanguageMapping } from 'app/shared/model/movie-language-mapping.model';
import { MovieLanguageMappingService } from './movie-language-mapping.service';

@Component({
    selector: 'jhi-movie-language-mapping-delete-dialog',
    templateUrl: './movie-language-mapping-delete-dialog.component.html'
})
export class MovieLanguageMappingDeleteDialogComponent {
    movieLanguageMapping: IMovieLanguageMapping;

    constructor(
        protected movieLanguageMappingService: MovieLanguageMappingService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.movieLanguageMappingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'movieLanguageMappingListModification',
                content: 'Deleted an movieLanguageMapping'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-movie-language-mapping-delete-popup',
    template: ''
})
export class MovieLanguageMappingDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ movieLanguageMapping }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MovieLanguageMappingDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.movieLanguageMapping = movieLanguageMapping;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/movie-language-mapping', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/movie-language-mapping', { outlets: { popup: null } }]);
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

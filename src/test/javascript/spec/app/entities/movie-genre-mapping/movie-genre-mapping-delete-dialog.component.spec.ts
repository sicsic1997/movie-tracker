/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MovieTrackerTestModule } from '../../../test.module';
import { MovieGenreMappingDeleteDialogComponent } from 'app/entities/movie-genre-mapping/movie-genre-mapping-delete-dialog.component';
import { MovieGenreMappingService } from 'app/entities/movie-genre-mapping/movie-genre-mapping.service';

describe('Component Tests', () => {
    describe('MovieGenreMapping Management Delete Component', () => {
        let comp: MovieGenreMappingDeleteDialogComponent;
        let fixture: ComponentFixture<MovieGenreMappingDeleteDialogComponent>;
        let service: MovieGenreMappingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [MovieGenreMappingDeleteDialogComponent]
            })
                .overrideTemplate(MovieGenreMappingDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MovieGenreMappingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MovieGenreMappingService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

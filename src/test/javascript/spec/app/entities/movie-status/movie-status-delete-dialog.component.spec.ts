/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MovieTrackerTestModule } from '../../../test.module';
import { MovieStatusDeleteDialogComponent } from 'app/entities/movie-status/movie-status-delete-dialog.component';
import { MovieStatusService } from 'app/entities/movie-status/movie-status.service';

describe('Component Tests', () => {
    describe('MovieStatus Management Delete Component', () => {
        let comp: MovieStatusDeleteDialogComponent;
        let fixture: ComponentFixture<MovieStatusDeleteDialogComponent>;
        let service: MovieStatusService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [MovieStatusDeleteDialogComponent]
            })
                .overrideTemplate(MovieStatusDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MovieStatusDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MovieStatusService);
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

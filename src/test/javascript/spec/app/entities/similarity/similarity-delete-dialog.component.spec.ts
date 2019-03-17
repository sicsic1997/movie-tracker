/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MovieTrackerTestModule } from '../../../test.module';
import { SimilarityDeleteDialogComponent } from 'app/entities/similarity/similarity-delete-dialog.component';
import { SimilarityService } from 'app/entities/similarity/similarity.service';

describe('Component Tests', () => {
    describe('Similarity Management Delete Component', () => {
        let comp: SimilarityDeleteDialogComponent;
        let fixture: ComponentFixture<SimilarityDeleteDialogComponent>;
        let service: SimilarityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [SimilarityDeleteDialogComponent]
            })
                .overrideTemplate(SimilarityDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SimilarityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SimilarityService);
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

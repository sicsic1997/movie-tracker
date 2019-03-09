/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MovieTrackerTestModule } from '../../../test.module';
import { RatedDeleteDialogComponent } from 'app/entities/rated/rated-delete-dialog.component';
import { RatedService } from 'app/entities/rated/rated.service';

describe('Component Tests', () => {
    describe('Rated Management Delete Component', () => {
        let comp: RatedDeleteDialogComponent;
        let fixture: ComponentFixture<RatedDeleteDialogComponent>;
        let service: RatedService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [RatedDeleteDialogComponent]
            })
                .overrideTemplate(RatedDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RatedDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RatedService);
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

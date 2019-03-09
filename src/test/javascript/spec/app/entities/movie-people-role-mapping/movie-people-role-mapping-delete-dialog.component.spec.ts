/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MovieTrackerTestModule } from '../../../test.module';
import { MoviePeopleRoleMappingDeleteDialogComponent } from 'app/entities/movie-people-role-mapping/movie-people-role-mapping-delete-dialog.component';
import { MoviePeopleRoleMappingService } from 'app/entities/movie-people-role-mapping/movie-people-role-mapping.service';

describe('Component Tests', () => {
    describe('MoviePeopleRoleMapping Management Delete Component', () => {
        let comp: MoviePeopleRoleMappingDeleteDialogComponent;
        let fixture: ComponentFixture<MoviePeopleRoleMappingDeleteDialogComponent>;
        let service: MoviePeopleRoleMappingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [MoviePeopleRoleMappingDeleteDialogComponent]
            })
                .overrideTemplate(MoviePeopleRoleMappingDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MoviePeopleRoleMappingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoviePeopleRoleMappingService);
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

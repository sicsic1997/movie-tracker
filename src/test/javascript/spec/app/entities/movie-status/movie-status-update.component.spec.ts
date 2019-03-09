/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MovieTrackerTestModule } from '../../../test.module';
import { MovieStatusUpdateComponent } from 'app/entities/movie-status/movie-status-update.component';
import { MovieStatusService } from 'app/entities/movie-status/movie-status.service';
import { MovieStatus } from 'app/shared/model/movie-status.model';

describe('Component Tests', () => {
    describe('MovieStatus Management Update Component', () => {
        let comp: MovieStatusUpdateComponent;
        let fixture: ComponentFixture<MovieStatusUpdateComponent>;
        let service: MovieStatusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [MovieStatusUpdateComponent]
            })
                .overrideTemplate(MovieStatusUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MovieStatusUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MovieStatusService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MovieStatus(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.movieStatus = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MovieStatus();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.movieStatus = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});

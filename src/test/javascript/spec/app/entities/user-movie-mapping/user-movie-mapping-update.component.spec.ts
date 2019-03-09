/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MovieTrackerTestModule } from '../../../test.module';
import { UserMovieMappingUpdateComponent } from 'app/entities/user-movie-mapping/user-movie-mapping-update.component';
import { UserMovieMappingService } from 'app/entities/user-movie-mapping/user-movie-mapping.service';
import { UserMovieMapping } from 'app/shared/model/user-movie-mapping.model';

describe('Component Tests', () => {
    describe('UserMovieMapping Management Update Component', () => {
        let comp: UserMovieMappingUpdateComponent;
        let fixture: ComponentFixture<UserMovieMappingUpdateComponent>;
        let service: UserMovieMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [UserMovieMappingUpdateComponent]
            })
                .overrideTemplate(UserMovieMappingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UserMovieMappingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserMovieMappingService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new UserMovieMapping(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.userMovieMapping = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new UserMovieMapping();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.userMovieMapping = entity;
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

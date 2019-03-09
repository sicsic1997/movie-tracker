/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MovieTrackerTestModule } from '../../../test.module';
import { MoviePeopleRoleMappingUpdateComponent } from 'app/entities/movie-people-role-mapping/movie-people-role-mapping-update.component';
import { MoviePeopleRoleMappingService } from 'app/entities/movie-people-role-mapping/movie-people-role-mapping.service';
import { MoviePeopleRoleMapping } from 'app/shared/model/movie-people-role-mapping.model';

describe('Component Tests', () => {
    describe('MoviePeopleRoleMapping Management Update Component', () => {
        let comp: MoviePeopleRoleMappingUpdateComponent;
        let fixture: ComponentFixture<MoviePeopleRoleMappingUpdateComponent>;
        let service: MoviePeopleRoleMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [MoviePeopleRoleMappingUpdateComponent]
            })
                .overrideTemplate(MoviePeopleRoleMappingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MoviePeopleRoleMappingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoviePeopleRoleMappingService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MoviePeopleRoleMapping(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.moviePeopleRoleMapping = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MoviePeopleRoleMapping();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.moviePeopleRoleMapping = entity;
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

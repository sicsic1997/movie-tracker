/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MovieTrackerTestModule } from '../../../test.module';
import { MovieGenreMappingUpdateComponent } from 'app/entities/movie-genre-mapping/movie-genre-mapping-update.component';
import { MovieGenreMappingService } from 'app/entities/movie-genre-mapping/movie-genre-mapping.service';
import { MovieGenreMapping } from 'app/shared/model/movie-genre-mapping.model';

describe('Component Tests', () => {
    describe('MovieGenreMapping Management Update Component', () => {
        let comp: MovieGenreMappingUpdateComponent;
        let fixture: ComponentFixture<MovieGenreMappingUpdateComponent>;
        let service: MovieGenreMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [MovieGenreMappingUpdateComponent]
            })
                .overrideTemplate(MovieGenreMappingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MovieGenreMappingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MovieGenreMappingService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MovieGenreMapping(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.movieGenreMapping = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MovieGenreMapping();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.movieGenreMapping = entity;
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

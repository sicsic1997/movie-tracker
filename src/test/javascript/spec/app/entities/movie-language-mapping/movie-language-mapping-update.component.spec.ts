/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MovieTrackerTestModule } from '../../../test.module';
import { MovieLanguageMappingUpdateComponent } from 'app/entities/movie-language-mapping/movie-language-mapping-update.component';
import { MovieLanguageMappingService } from 'app/entities/movie-language-mapping/movie-language-mapping.service';
import { MovieLanguageMapping } from 'app/shared/model/movie-language-mapping.model';

describe('Component Tests', () => {
    describe('MovieLanguageMapping Management Update Component', () => {
        let comp: MovieLanguageMappingUpdateComponent;
        let fixture: ComponentFixture<MovieLanguageMappingUpdateComponent>;
        let service: MovieLanguageMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [MovieLanguageMappingUpdateComponent]
            })
                .overrideTemplate(MovieLanguageMappingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MovieLanguageMappingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MovieLanguageMappingService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new MovieLanguageMapping(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.movieLanguageMapping = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new MovieLanguageMapping();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.movieLanguageMapping = entity;
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

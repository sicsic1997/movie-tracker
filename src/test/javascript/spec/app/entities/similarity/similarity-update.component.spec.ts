/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MovieTrackerTestModule } from '../../../test.module';
import { SimilarityUpdateComponent } from 'app/entities/similarity/similarity-update.component';
import { SimilarityService } from 'app/entities/similarity/similarity.service';
import { Similarity } from 'app/shared/model/similarity.model';

describe('Component Tests', () => {
    describe('Similarity Management Update Component', () => {
        let comp: SimilarityUpdateComponent;
        let fixture: ComponentFixture<SimilarityUpdateComponent>;
        let service: SimilarityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [SimilarityUpdateComponent]
            })
                .overrideTemplate(SimilarityUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SimilarityUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SimilarityService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Similarity(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.similarity = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Similarity();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.similarity = entity;
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

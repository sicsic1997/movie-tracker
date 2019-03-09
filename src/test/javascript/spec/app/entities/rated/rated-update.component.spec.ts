/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MovieTrackerTestModule } from '../../../test.module';
import { RatedUpdateComponent } from 'app/entities/rated/rated-update.component';
import { RatedService } from 'app/entities/rated/rated.service';
import { Rated } from 'app/shared/model/rated.model';

describe('Component Tests', () => {
    describe('Rated Management Update Component', () => {
        let comp: RatedUpdateComponent;
        let fixture: ComponentFixture<RatedUpdateComponent>;
        let service: RatedService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [RatedUpdateComponent]
            })
                .overrideTemplate(RatedUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RatedUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RatedService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Rated(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rated = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Rated();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rated = entity;
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MovieTrackerTestModule } from '../../../test.module';
import { RatedDetailComponent } from 'app/entities/rated/rated-detail.component';
import { Rated } from 'app/shared/model/rated.model';

describe('Component Tests', () => {
    describe('Rated Management Detail Component', () => {
        let comp: RatedDetailComponent;
        let fixture: ComponentFixture<RatedDetailComponent>;
        const route = ({ data: of({ rated: new Rated(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [RatedDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RatedDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RatedDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rated).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

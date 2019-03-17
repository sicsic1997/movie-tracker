/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MovieTrackerTestModule } from '../../../test.module';
import { SimilarityDetailComponent } from 'app/entities/similarity/similarity-detail.component';
import { Similarity } from 'app/shared/model/similarity.model';

describe('Component Tests', () => {
    describe('Similarity Management Detail Component', () => {
        let comp: SimilarityDetailComponent;
        let fixture: ComponentFixture<SimilarityDetailComponent>;
        const route = ({ data: of({ similarity: new Similarity(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [SimilarityDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SimilarityDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SimilarityDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.similarity).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

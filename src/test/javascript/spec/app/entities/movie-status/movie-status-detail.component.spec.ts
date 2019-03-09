/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MovieTrackerTestModule } from '../../../test.module';
import { MovieStatusDetailComponent } from 'app/entities/movie-status/movie-status-detail.component';
import { MovieStatus } from 'app/shared/model/movie-status.model';

describe('Component Tests', () => {
    describe('MovieStatus Management Detail Component', () => {
        let comp: MovieStatusDetailComponent;
        let fixture: ComponentFixture<MovieStatusDetailComponent>;
        const route = ({ data: of({ movieStatus: new MovieStatus(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [MovieStatusDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MovieStatusDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MovieStatusDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.movieStatus).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

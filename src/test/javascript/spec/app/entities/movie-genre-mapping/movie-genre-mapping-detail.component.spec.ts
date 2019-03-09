/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MovieTrackerTestModule } from '../../../test.module';
import { MovieGenreMappingDetailComponent } from 'app/entities/movie-genre-mapping/movie-genre-mapping-detail.component';
import { MovieGenreMapping } from 'app/shared/model/movie-genre-mapping.model';

describe('Component Tests', () => {
    describe('MovieGenreMapping Management Detail Component', () => {
        let comp: MovieGenreMappingDetailComponent;
        let fixture: ComponentFixture<MovieGenreMappingDetailComponent>;
        const route = ({ data: of({ movieGenreMapping: new MovieGenreMapping(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [MovieGenreMappingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MovieGenreMappingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MovieGenreMappingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.movieGenreMapping).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

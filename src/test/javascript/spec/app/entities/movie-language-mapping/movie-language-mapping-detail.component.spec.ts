/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MovieTrackerTestModule } from '../../../test.module';
import { MovieLanguageMappingDetailComponent } from 'app/entities/movie-language-mapping/movie-language-mapping-detail.component';
import { MovieLanguageMapping } from 'app/shared/model/movie-language-mapping.model';

describe('Component Tests', () => {
    describe('MovieLanguageMapping Management Detail Component', () => {
        let comp: MovieLanguageMappingDetailComponent;
        let fixture: ComponentFixture<MovieLanguageMappingDetailComponent>;
        const route = ({ data: of({ movieLanguageMapping: new MovieLanguageMapping(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [MovieLanguageMappingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MovieLanguageMappingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MovieLanguageMappingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.movieLanguageMapping).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

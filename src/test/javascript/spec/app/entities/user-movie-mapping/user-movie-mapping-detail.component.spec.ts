/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MovieTrackerTestModule } from '../../../test.module';
import { UserMovieMappingDetailComponent } from 'app/entities/user-movie-mapping/user-movie-mapping-detail.component';
import { UserMovieMapping } from 'app/shared/model/user-movie-mapping.model';

describe('Component Tests', () => {
    describe('UserMovieMapping Management Detail Component', () => {
        let comp: UserMovieMappingDetailComponent;
        let fixture: ComponentFixture<UserMovieMappingDetailComponent>;
        const route = ({ data: of({ userMovieMapping: new UserMovieMapping(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [UserMovieMappingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UserMovieMappingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserMovieMappingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.userMovieMapping).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

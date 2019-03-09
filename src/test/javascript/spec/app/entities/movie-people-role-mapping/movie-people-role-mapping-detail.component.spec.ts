/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MovieTrackerTestModule } from '../../../test.module';
import { MoviePeopleRoleMappingDetailComponent } from 'app/entities/movie-people-role-mapping/movie-people-role-mapping-detail.component';
import { MoviePeopleRoleMapping } from 'app/shared/model/movie-people-role-mapping.model';

describe('Component Tests', () => {
    describe('MoviePeopleRoleMapping Management Detail Component', () => {
        let comp: MoviePeopleRoleMappingDetailComponent;
        let fixture: ComponentFixture<MoviePeopleRoleMappingDetailComponent>;
        const route = ({ data: of({ moviePeopleRoleMapping: new MoviePeopleRoleMapping(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MovieTrackerTestModule],
                declarations: [MoviePeopleRoleMappingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MoviePeopleRoleMappingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MoviePeopleRoleMappingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.moviePeopleRoleMapping).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

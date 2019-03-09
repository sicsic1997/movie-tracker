import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMoviePeopleRoleMapping } from 'app/shared/model/movie-people-role-mapping.model';

@Component({
    selector: 'jhi-movie-people-role-mapping-detail',
    templateUrl: './movie-people-role-mapping-detail.component.html'
})
export class MoviePeopleRoleMappingDetailComponent implements OnInit {
    moviePeopleRoleMapping: IMoviePeopleRoleMapping;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ moviePeopleRoleMapping }) => {
            this.moviePeopleRoleMapping = moviePeopleRoleMapping;
        });
    }

    previousState() {
        window.history.back();
    }
}

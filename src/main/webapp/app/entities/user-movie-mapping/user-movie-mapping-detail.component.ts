import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserMovieMapping } from 'app/shared/model/user-movie-mapping.model';

@Component({
    selector: 'jhi-user-movie-mapping-detail',
    templateUrl: './user-movie-mapping-detail.component.html'
})
export class UserMovieMappingDetailComponent implements OnInit {
    userMovieMapping: IUserMovieMapping;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userMovieMapping }) => {
            this.userMovieMapping = userMovieMapping;
        });
    }

    previousState() {
        window.history.back();
    }
}

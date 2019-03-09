import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMovieStatus } from 'app/shared/model/movie-status.model';

@Component({
    selector: 'jhi-movie-status-detail',
    templateUrl: './movie-status-detail.component.html'
})
export class MovieStatusDetailComponent implements OnInit {
    movieStatus: IMovieStatus;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ movieStatus }) => {
            this.movieStatus = movieStatus;
        });
    }

    previousState() {
        window.history.back();
    }
}

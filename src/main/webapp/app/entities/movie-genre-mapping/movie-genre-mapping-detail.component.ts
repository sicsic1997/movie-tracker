import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMovieGenreMapping } from 'app/shared/model/movie-genre-mapping.model';

@Component({
    selector: 'jhi-movie-genre-mapping-detail',
    templateUrl: './movie-genre-mapping-detail.component.html'
})
export class MovieGenreMappingDetailComponent implements OnInit {
    movieGenreMapping: IMovieGenreMapping;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ movieGenreMapping }) => {
            this.movieGenreMapping = movieGenreMapping;
        });
    }

    previousState() {
        window.history.back();
    }
}

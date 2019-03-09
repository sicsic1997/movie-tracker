import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMovieLanguageMapping } from 'app/shared/model/movie-language-mapping.model';

@Component({
    selector: 'jhi-movie-language-mapping-detail',
    templateUrl: './movie-language-mapping-detail.component.html'
})
export class MovieLanguageMappingDetailComponent implements OnInit {
    movieLanguageMapping: IMovieLanguageMapping;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ movieLanguageMapping }) => {
            this.movieLanguageMapping = movieLanguageMapping;
        });
    }

    previousState() {
        window.history.back();
    }
}

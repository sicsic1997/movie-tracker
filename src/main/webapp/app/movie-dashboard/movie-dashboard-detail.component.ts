import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IMovie } from 'app/shared/model/movie.model';
import { MovieDashboardService } from 'app/movie-dashboard/movie-dashboard.service';
import { JhiAlertService } from 'ng-jhipster';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-movie-detail',
    templateUrl: './movie-dashboard-detail.component.html',
    styleUrls: ['movie-dashboard-detail.css']
})
export class MovieDashboardDetailComponent implements OnInit {
    movie: IMovie;
    suggestions: IMovie[];

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected movieDashboardService: MovieDashboardService,
        protected jhiAlertService: JhiAlertService
    ) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ movie }) => {
            this.movie = movie;
            this.movieDashboardService
                .querySuggestions(this.movie.id)
                .subscribe(
                    (res: HttpResponse<IMovie[]>) => (this.suggestions = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        });
    }

    previousState() {
        window.history.back();
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

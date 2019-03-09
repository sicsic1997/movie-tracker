import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMoviePeopleRoleMapping } from 'app/shared/model/movie-people-role-mapping.model';
import { MoviePeopleRoleMappingService } from './movie-people-role-mapping.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people';
import { IMovie } from 'app/shared/model/movie.model';
import { MovieService } from 'app/entities/movie';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from 'app/entities/role';

@Component({
    selector: 'jhi-movie-people-role-mapping-update',
    templateUrl: './movie-people-role-mapping-update.component.html'
})
export class MoviePeopleRoleMappingUpdateComponent implements OnInit {
    moviePeopleRoleMapping: IMoviePeopleRoleMapping;
    isSaving: boolean;

    people: IPeople[];

    movies: IMovie[];

    roles: IRole[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected moviePeopleRoleMappingService: MoviePeopleRoleMappingService,
        protected peopleService: PeopleService,
        protected movieService: MovieService,
        protected roleService: RoleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ moviePeopleRoleMapping }) => {
            this.moviePeopleRoleMapping = moviePeopleRoleMapping;
        });
        this.peopleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPeople[]>) => response.body)
            )
            .subscribe((res: IPeople[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.movieService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMovie[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMovie[]>) => response.body)
            )
            .subscribe((res: IMovie[]) => (this.movies = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.roleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRole[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRole[]>) => response.body)
            )
            .subscribe((res: IRole[]) => (this.roles = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.moviePeopleRoleMapping.id !== undefined) {
            this.subscribeToSaveResponse(this.moviePeopleRoleMappingService.update(this.moviePeopleRoleMapping));
        } else {
            this.subscribeToSaveResponse(this.moviePeopleRoleMappingService.create(this.moviePeopleRoleMapping));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMoviePeopleRoleMapping>>) {
        result.subscribe(
            (res: HttpResponse<IMoviePeopleRoleMapping>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPeopleById(index: number, item: IPeople) {
        return item.id;
    }

    trackMovieById(index: number, item: IMovie) {
        return item.id;
    }

    trackRoleById(index: number, item: IRole) {
        return item.id;
    }
}

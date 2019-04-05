import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserMovieMapping } from 'app/shared/model/user-movie-mapping.model';
import { IMovie } from 'app/shared/model/movie.model';

type EntityResponseType = HttpResponse<IUserMovieMapping>;
type EntityArrayResponseType = HttpResponse<IUserMovieMapping[]>;

@Injectable({ providedIn: 'root' })
export class UserMovieMappingService {
    public resourceUrl = SERVER_API_URL + 'api/user-movie-mappings';

    constructor(protected http: HttpClient) {}

    create(userMovieMapping: IUserMovieMapping): Observable<EntityResponseType> {
        return this.http.post<IUserMovieMapping>(this.resourceUrl, userMovieMapping, { observe: 'response' });
    }

    update(userMovieMapping: IUserMovieMapping): Observable<EntityResponseType> {
        return this.http.put<IUserMovieMapping>(this.resourceUrl, userMovieMapping, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUserMovieMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUserMovieMapping[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    findByMovieAndLogin(movieId?: number): Observable<EntityArrayResponseType> {
        const options = createRequestOption({ movieId: movieId });
        return this.http.get<IMovie[]>(this.resourceUrl + '/user-movie', { params: options, observe: 'response' });
    }

    findByLogin(movieId?: number): Observable<EntityArrayResponseType> {
        const options = createRequestOption({ movieId: movieId });
        return this.http.get<IMovie[]>(this.resourceUrl + '/user', { params: options, observe: 'response' });
    }

    deleteByMovieId(movieId: number, movieStatusCode: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(this.resourceUrl + '/user/' + movieId + '/' + movieStatusCode, { observe: 'response' });
    }

    createByMovieIdAndStatusCode(movieId: number, movieStatusCode: string): Observable<HttpResponse<any>> {
        const options = createRequestOption({ movieId: movieId, movieStatusCode: movieStatusCode });
        return this.http.get<any>(this.resourceUrl + '/save', { params: options, observe: 'response' });
    }
}

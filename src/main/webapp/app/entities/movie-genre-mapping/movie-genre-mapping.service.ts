import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMovieGenreMapping } from 'app/shared/model/movie-genre-mapping.model';

type EntityResponseType = HttpResponse<IMovieGenreMapping>;
type EntityArrayResponseType = HttpResponse<IMovieGenreMapping[]>;

@Injectable({ providedIn: 'root' })
export class MovieGenreMappingService {
    public resourceUrl = SERVER_API_URL + 'api/movie-genre-mappings';

    constructor(protected http: HttpClient) {}

    create(movieGenreMapping: IMovieGenreMapping): Observable<EntityResponseType> {
        return this.http.post<IMovieGenreMapping>(this.resourceUrl, movieGenreMapping, { observe: 'response' });
    }

    update(movieGenreMapping: IMovieGenreMapping): Observable<EntityResponseType> {
        return this.http.put<IMovieGenreMapping>(this.resourceUrl, movieGenreMapping, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMovieGenreMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMovieGenreMapping[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

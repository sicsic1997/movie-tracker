import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMovieStatus } from 'app/shared/model/movie-status.model';

type EntityResponseType = HttpResponse<IMovieStatus>;
type EntityArrayResponseType = HttpResponse<IMovieStatus[]>;

@Injectable({ providedIn: 'root' })
export class MovieStatusService {
    public resourceUrl = SERVER_API_URL + 'api/movie-statuses';

    constructor(protected http: HttpClient) {}

    create(movieStatus: IMovieStatus): Observable<EntityResponseType> {
        return this.http.post<IMovieStatus>(this.resourceUrl, movieStatus, { observe: 'response' });
    }

    update(movieStatus: IMovieStatus): Observable<EntityResponseType> {
        return this.http.put<IMovieStatus>(this.resourceUrl, movieStatus, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMovieStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMovieStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

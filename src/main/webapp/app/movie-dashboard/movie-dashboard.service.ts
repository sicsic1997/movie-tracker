import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMovie } from 'app/shared/model/movie.model';

type EntityResponseType = HttpResponse<IMovie>;
type EntityArrayResponseType = HttpResponse<IMovie[]>;

@Injectable({ providedIn: 'root' })
export class MovieDashboardService {
    public resourceUrl = SERVER_API_URL + 'api/movie-dashboard';

    constructor(protected http: HttpClient) {}

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMovie[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMovie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

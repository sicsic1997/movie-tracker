import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMovie } from 'app/shared/model/movie.model';

type EntityResponseType = HttpResponse<IMovie>;
type EntityArrayResponseType = HttpResponse<IMovie[]>;

@Injectable({ providedIn: 'root' })
export class UserDashboardService {
    public resourceUrl = SERVER_API_URL + 'api/user-dashboard';

    constructor(protected http: HttpClient) {}

    findByLoginInHistory(): Observable<EntityArrayResponseType> {
        return this.http.get<IMovie[]>(this.resourceUrl + '/history', { observe: 'response' });
    }

    findByLoginInWishlist(): Observable<EntityArrayResponseType> {
        return this.http.get<IMovie[]>(this.resourceUrl + '/wishlist', { observe: 'response' });
    }

    querySuggestions(): Observable<EntityArrayResponseType> {
        return this.http.get<IMovie[]>(SERVER_API_URL + 'api/suggestion/user', { observe: 'response' });
    }
}

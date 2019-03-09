import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMovieLanguageMapping } from 'app/shared/model/movie-language-mapping.model';

type EntityResponseType = HttpResponse<IMovieLanguageMapping>;
type EntityArrayResponseType = HttpResponse<IMovieLanguageMapping[]>;

@Injectable({ providedIn: 'root' })
export class MovieLanguageMappingService {
    public resourceUrl = SERVER_API_URL + 'api/movie-language-mappings';

    constructor(protected http: HttpClient) {}

    create(movieLanguageMapping: IMovieLanguageMapping): Observable<EntityResponseType> {
        return this.http.post<IMovieLanguageMapping>(this.resourceUrl, movieLanguageMapping, { observe: 'response' });
    }

    update(movieLanguageMapping: IMovieLanguageMapping): Observable<EntityResponseType> {
        return this.http.put<IMovieLanguageMapping>(this.resourceUrl, movieLanguageMapping, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMovieLanguageMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMovieLanguageMapping[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

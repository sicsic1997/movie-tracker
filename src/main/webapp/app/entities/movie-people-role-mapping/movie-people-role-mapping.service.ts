import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMoviePeopleRoleMapping } from 'app/shared/model/movie-people-role-mapping.model';

type EntityResponseType = HttpResponse<IMoviePeopleRoleMapping>;
type EntityArrayResponseType = HttpResponse<IMoviePeopleRoleMapping[]>;

@Injectable({ providedIn: 'root' })
export class MoviePeopleRoleMappingService {
    public resourceUrl = SERVER_API_URL + 'api/movie-people-role-mappings';

    constructor(protected http: HttpClient) {}

    create(moviePeopleRoleMapping: IMoviePeopleRoleMapping): Observable<EntityResponseType> {
        return this.http.post<IMoviePeopleRoleMapping>(this.resourceUrl, moviePeopleRoleMapping, { observe: 'response' });
    }

    update(moviePeopleRoleMapping: IMoviePeopleRoleMapping): Observable<EntityResponseType> {
        return this.http.put<IMoviePeopleRoleMapping>(this.resourceUrl, moviePeopleRoleMapping, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMoviePeopleRoleMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMoviePeopleRoleMapping[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

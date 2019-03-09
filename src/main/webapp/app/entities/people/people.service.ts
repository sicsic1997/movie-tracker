import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPeople } from 'app/shared/model/people.model';

type EntityResponseType = HttpResponse<IPeople>;
type EntityArrayResponseType = HttpResponse<IPeople[]>;

@Injectable({ providedIn: 'root' })
export class PeopleService {
    public resourceUrl = SERVER_API_URL + 'api/people';

    constructor(protected http: HttpClient) {}

    create(people: IPeople): Observable<EntityResponseType> {
        return this.http.post<IPeople>(this.resourceUrl, people, { observe: 'response' });
    }

    update(people: IPeople): Observable<EntityResponseType> {
        return this.http.put<IPeople>(this.resourceUrl, people, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPeople>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPeople[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

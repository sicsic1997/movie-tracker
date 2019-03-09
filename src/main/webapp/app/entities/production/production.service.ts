import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProduction } from 'app/shared/model/production.model';

type EntityResponseType = HttpResponse<IProduction>;
type EntityArrayResponseType = HttpResponse<IProduction[]>;

@Injectable({ providedIn: 'root' })
export class ProductionService {
    public resourceUrl = SERVER_API_URL + 'api/productions';

    constructor(protected http: HttpClient) {}

    create(production: IProduction): Observable<EntityResponseType> {
        return this.http.post<IProduction>(this.resourceUrl, production, { observe: 'response' });
    }

    update(production: IProduction): Observable<EntityResponseType> {
        return this.http.put<IProduction>(this.resourceUrl, production, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProduction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProduction[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

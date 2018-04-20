import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ParentMySuffix } from './parent-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ParentMySuffix>;

@Injectable()
export class ParentMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/parents';

    constructor(private http: HttpClient) { }

    create(parent: ParentMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(parent);
        return this.http.post<ParentMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(parent: ParentMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(parent);
        return this.http.put<ParentMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ParentMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ParentMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<ParentMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ParentMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ParentMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ParentMySuffix[]>): HttpResponse<ParentMySuffix[]> {
        const jsonResponse: ParentMySuffix[] = res.body;
        const body: ParentMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ParentMySuffix.
     */
    private convertItemFromServer(parent: ParentMySuffix): ParentMySuffix {
        const copy: ParentMySuffix = Object.assign({}, parent);
        return copy;
    }

    /**
     * Convert a ParentMySuffix to a JSON which can be sent to the server.
     */
    private convert(parent: ParentMySuffix): ParentMySuffix {
        const copy: ParentMySuffix = Object.assign({}, parent);
        return copy;
    }
}

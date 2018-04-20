import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { EnseignantMySuffix } from './enseignant-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EnseignantMySuffix>;

@Injectable()
export class EnseignantMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/enseignants';

    constructor(private http: HttpClient) { }

    create(enseignant: EnseignantMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(enseignant);
        return this.http.post<EnseignantMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(enseignant: EnseignantMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(enseignant);
        return this.http.put<EnseignantMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EnseignantMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EnseignantMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<EnseignantMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EnseignantMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EnseignantMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EnseignantMySuffix[]>): HttpResponse<EnseignantMySuffix[]> {
        const jsonResponse: EnseignantMySuffix[] = res.body;
        const body: EnseignantMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EnseignantMySuffix.
     */
    private convertItemFromServer(enseignant: EnseignantMySuffix): EnseignantMySuffix {
        const copy: EnseignantMySuffix = Object.assign({}, enseignant);
        return copy;
    }

    /**
     * Convert a EnseignantMySuffix to a JSON which can be sent to the server.
     */
    private convert(enseignant: EnseignantMySuffix): EnseignantMySuffix {
        const copy: EnseignantMySuffix = Object.assign({}, enseignant);
        return copy;
    }
}

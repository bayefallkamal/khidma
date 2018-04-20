import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PaiementMySuffix } from './paiement-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PaiementMySuffix>;

@Injectable()
export class PaiementMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/paiements';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(paiement: PaiementMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(paiement);
        return this.http.post<PaiementMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(paiement: PaiementMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(paiement);
        return this.http.put<PaiementMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PaiementMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PaiementMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<PaiementMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PaiementMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PaiementMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PaiementMySuffix[]>): HttpResponse<PaiementMySuffix[]> {
        const jsonResponse: PaiementMySuffix[] = res.body;
        const body: PaiementMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PaiementMySuffix.
     */
    private convertItemFromServer(paiement: PaiementMySuffix): PaiementMySuffix {
        const copy: PaiementMySuffix = Object.assign({}, paiement);
        copy.datePaiement = this.dateUtils
            .convertDateTimeFromServer(paiement.datePaiement);
        return copy;
    }

    /**
     * Convert a PaiementMySuffix to a JSON which can be sent to the server.
     */
    private convert(paiement: PaiementMySuffix): PaiementMySuffix {
        const copy: PaiementMySuffix = Object.assign({}, paiement);

        copy.datePaiement = this.dateUtils.toDate(paiement.datePaiement);
        return copy;
    }
}

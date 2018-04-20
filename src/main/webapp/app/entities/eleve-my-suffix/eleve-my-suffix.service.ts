import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { EleveMySuffix } from './eleve-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EleveMySuffix>;

@Injectable()
export class EleveMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/eleves';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(eleve: EleveMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(eleve);
        return this.http.post<EleveMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(eleve: EleveMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(eleve);
        return this.http.put<EleveMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EleveMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EleveMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<EleveMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EleveMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EleveMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EleveMySuffix[]>): HttpResponse<EleveMySuffix[]> {
        const jsonResponse: EleveMySuffix[] = res.body;
        const body: EleveMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EleveMySuffix.
     */
    private convertItemFromServer(eleve: EleveMySuffix): EleveMySuffix {
        const copy: EleveMySuffix = Object.assign({}, eleve);
        copy.dateNaiss = this.dateUtils
            .convertDateTimeFromServer(eleve.dateNaiss);
        copy.dateEntree = this.dateUtils
            .convertDateTimeFromServer(eleve.dateEntree);
        return copy;
    }

    /**
     * Convert a EleveMySuffix to a JSON which can be sent to the server.
     */
    private convert(eleve: EleveMySuffix): EleveMySuffix {
        const copy: EleveMySuffix = Object.assign({}, eleve);

        copy.dateNaiss = this.dateUtils.toDate(eleve.dateNaiss);

        copy.dateEntree = this.dateUtils.toDate(eleve.dateEntree);
        return copy;
    }
}

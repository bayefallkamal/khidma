import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { EleveMySuffix } from './eleve-my-suffix.model';
import { EleveMySuffixService } from './eleve-my-suffix.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-eleve-my-suffix',
    templateUrl: './eleve-my-suffix.component.html'
})
export class EleveMySuffixComponent implements OnInit, OnDestroy {
eleves: EleveMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private eleveService: EleveMySuffixService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.eleveService.query().subscribe(
            (res: HttpResponse<EleveMySuffix[]>) => {
                this.eleves = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEleves();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EleveMySuffix) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInEleves() {
        this.eventSubscriber = this.eventManager.subscribe('eleveListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PaiementMySuffix } from './paiement-my-suffix.model';
import { PaiementMySuffixService } from './paiement-my-suffix.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-paiement-my-suffix',
    templateUrl: './paiement-my-suffix.component.html'
})
export class PaiementMySuffixComponent implements OnInit, OnDestroy {
paiements: PaiementMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private paiementService: PaiementMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.paiementService.query().subscribe(
            (res: HttpResponse<PaiementMySuffix[]>) => {
                this.paiements = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPaiements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PaiementMySuffix) {
        return item.id;
    }
    registerChangeInPaiements() {
        this.eventSubscriber = this.eventManager.subscribe('paiementListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

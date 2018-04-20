import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EnseignantMySuffix } from './enseignant-my-suffix.model';
import { EnseignantMySuffixService } from './enseignant-my-suffix.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-enseignant-my-suffix',
    templateUrl: './enseignant-my-suffix.component.html'
})
export class EnseignantMySuffixComponent implements OnInit, OnDestroy {
enseignants: EnseignantMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private enseignantService: EnseignantMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.enseignantService.query().subscribe(
            (res: HttpResponse<EnseignantMySuffix[]>) => {
                this.enseignants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEnseignants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EnseignantMySuffix) {
        return item.id;
    }
    registerChangeInEnseignants() {
        this.eventSubscriber = this.eventManager.subscribe('enseignantListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

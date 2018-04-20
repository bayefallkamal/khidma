import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PaiementMySuffix } from './paiement-my-suffix.model';
import { PaiementMySuffixService } from './paiement-my-suffix.service';

@Component({
    selector: 'jhi-paiement-my-suffix-detail',
    templateUrl: './paiement-my-suffix-detail.component.html'
})
export class PaiementMySuffixDetailComponent implements OnInit, OnDestroy {

    paiement: PaiementMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private paiementService: PaiementMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPaiements();
    }

    load(id) {
        this.paiementService.find(id)
            .subscribe((paiementResponse: HttpResponse<PaiementMySuffix>) => {
                this.paiement = paiementResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPaiements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'paiementListModification',
            (response) => this.load(this.paiement.id)
        );
    }
}

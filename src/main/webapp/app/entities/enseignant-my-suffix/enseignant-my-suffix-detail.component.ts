import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EnseignantMySuffix } from './enseignant-my-suffix.model';
import { EnseignantMySuffixService } from './enseignant-my-suffix.service';

@Component({
    selector: 'jhi-enseignant-my-suffix-detail',
    templateUrl: './enseignant-my-suffix-detail.component.html'
})
export class EnseignantMySuffixDetailComponent implements OnInit, OnDestroy {

    enseignant: EnseignantMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private enseignantService: EnseignantMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEnseignants();
    }

    load(id) {
        this.enseignantService.find(id)
            .subscribe((enseignantResponse: HttpResponse<EnseignantMySuffix>) => {
                this.enseignant = enseignantResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEnseignants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'enseignantListModification',
            (response) => this.load(this.enseignant.id)
        );
    }
}

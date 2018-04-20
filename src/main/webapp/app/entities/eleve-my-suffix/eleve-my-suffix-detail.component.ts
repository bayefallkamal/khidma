import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { EleveMySuffix } from './eleve-my-suffix.model';
import { EleveMySuffixService } from './eleve-my-suffix.service';

@Component({
    selector: 'jhi-eleve-my-suffix-detail',
    templateUrl: './eleve-my-suffix-detail.component.html'
})
export class EleveMySuffixDetailComponent implements OnInit, OnDestroy {

    eleve: EleveMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private eleveService: EleveMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEleves();
    }

    load(id) {
        this.eleveService.find(id)
            .subscribe((eleveResponse: HttpResponse<EleveMySuffix>) => {
                this.eleve = eleveResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEleves() {
        this.eventSubscriber = this.eventManager.subscribe(
            'eleveListModification',
            (response) => this.load(this.eleve.id)
        );
    }
}

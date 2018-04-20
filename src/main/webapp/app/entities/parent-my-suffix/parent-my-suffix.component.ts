import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ParentMySuffix } from './parent-my-suffix.model';
import { ParentMySuffixService } from './parent-my-suffix.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-parent-my-suffix',
    templateUrl: './parent-my-suffix.component.html'
})
export class ParentMySuffixComponent implements OnInit, OnDestroy {
parents: ParentMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private parentService: ParentMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.parentService.query().subscribe(
            (res: HttpResponse<ParentMySuffix[]>) => {
                this.parents = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInParents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ParentMySuffix) {
        return item.id;
    }
    registerChangeInParents() {
        this.eventSubscriber = this.eventManager.subscribe('parentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

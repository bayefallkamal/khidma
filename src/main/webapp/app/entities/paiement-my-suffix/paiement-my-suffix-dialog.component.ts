import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PaiementMySuffix } from './paiement-my-suffix.model';
import { PaiementMySuffixPopupService } from './paiement-my-suffix-popup.service';
import { PaiementMySuffixService } from './paiement-my-suffix.service';
import { EleveMySuffix, EleveMySuffixService } from '../eleve-my-suffix';

@Component({
    selector: 'jhi-paiement-my-suffix-dialog',
    templateUrl: './paiement-my-suffix-dialog.component.html'
})
export class PaiementMySuffixDialogComponent implements OnInit {

    paiement: PaiementMySuffix;
    isSaving: boolean;

    eleves: EleveMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private paiementService: PaiementMySuffixService,
        private eleveService: EleveMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.eleveService.query()
            .subscribe((res: HttpResponse<EleveMySuffix[]>) => { this.eleves = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.paiement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.paiementService.update(this.paiement));
        } else {
            this.subscribeToSaveResponse(
                this.paiementService.create(this.paiement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PaiementMySuffix>>) {
        result.subscribe((res: HttpResponse<PaiementMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PaiementMySuffix) {
        this.eventManager.broadcast({ name: 'paiementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackEleveById(index: number, item: EleveMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-paiement-my-suffix-popup',
    template: ''
})
export class PaiementMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paiementPopupService: PaiementMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.paiementPopupService
                    .open(PaiementMySuffixDialogComponent as Component, params['id']);
            } else {
                this.paiementPopupService
                    .open(PaiementMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

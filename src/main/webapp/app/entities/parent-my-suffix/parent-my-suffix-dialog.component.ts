import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ParentMySuffix } from './parent-my-suffix.model';
import { ParentMySuffixPopupService } from './parent-my-suffix-popup.service';
import { ParentMySuffixService } from './parent-my-suffix.service';
import { EleveMySuffix, EleveMySuffixService } from '../eleve-my-suffix';

@Component({
    selector: 'jhi-parent-my-suffix-dialog',
    templateUrl: './parent-my-suffix-dialog.component.html'
})
export class ParentMySuffixDialogComponent implements OnInit {

    parent: ParentMySuffix;
    isSaving: boolean;

    eleves: EleveMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private parentService: ParentMySuffixService,
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
        if (this.parent.id !== undefined) {
            this.subscribeToSaveResponse(
                this.parentService.update(this.parent));
        } else {
            this.subscribeToSaveResponse(
                this.parentService.create(this.parent));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ParentMySuffix>>) {
        result.subscribe((res: HttpResponse<ParentMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ParentMySuffix) {
        this.eventManager.broadcast({ name: 'parentListModification', content: 'OK'});
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
    selector: 'jhi-parent-my-suffix-popup',
    template: ''
})
export class ParentMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parentPopupService: ParentMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.parentPopupService
                    .open(ParentMySuffixDialogComponent as Component, params['id']);
            } else {
                this.parentPopupService
                    .open(ParentMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

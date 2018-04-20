import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EnseignantMySuffix } from './enseignant-my-suffix.model';
import { EnseignantMySuffixPopupService } from './enseignant-my-suffix-popup.service';
import { EnseignantMySuffixService } from './enseignant-my-suffix.service';
import { EleveMySuffix, EleveMySuffixService } from '../eleve-my-suffix';

@Component({
    selector: 'jhi-enseignant-my-suffix-dialog',
    templateUrl: './enseignant-my-suffix-dialog.component.html'
})
export class EnseignantMySuffixDialogComponent implements OnInit {

    enseignant: EnseignantMySuffix;
    isSaving: boolean;

    eleves: EleveMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private enseignantService: EnseignantMySuffixService,
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
        if (this.enseignant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.enseignantService.update(this.enseignant));
        } else {
            this.subscribeToSaveResponse(
                this.enseignantService.create(this.enseignant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EnseignantMySuffix>>) {
        result.subscribe((res: HttpResponse<EnseignantMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EnseignantMySuffix) {
        this.eventManager.broadcast({ name: 'enseignantListModification', content: 'OK'});
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

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-enseignant-my-suffix-popup',
    template: ''
})
export class EnseignantMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private enseignantPopupService: EnseignantMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.enseignantPopupService
                    .open(EnseignantMySuffixDialogComponent as Component, params['id']);
            } else {
                this.enseignantPopupService
                    .open(EnseignantMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

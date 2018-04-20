import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { EleveMySuffix } from './eleve-my-suffix.model';
import { EleveMySuffixPopupService } from './eleve-my-suffix-popup.service';
import { EleveMySuffixService } from './eleve-my-suffix.service';
import { EnseignantMySuffix, EnseignantMySuffixService } from '../enseignant-my-suffix';

@Component({
    selector: 'jhi-eleve-my-suffix-dialog',
    templateUrl: './eleve-my-suffix-dialog.component.html'
})
export class EleveMySuffixDialogComponent implements OnInit {

    eleve: EleveMySuffix;
    isSaving: boolean;

    enseignants: EnseignantMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private eleveService: EleveMySuffixService,
        private enseignantService: EnseignantMySuffixService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.enseignantService.query()
            .subscribe((res: HttpResponse<EnseignantMySuffix[]>) => { this.enseignants = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.eleve, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.eleve.id !== undefined) {
            this.subscribeToSaveResponse(
                this.eleveService.update(this.eleve));
        } else {
            this.subscribeToSaveResponse(
                this.eleveService.create(this.eleve));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EleveMySuffix>>) {
        result.subscribe((res: HttpResponse<EleveMySuffix>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EleveMySuffix) {
        this.eventManager.broadcast({ name: 'eleveListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackEnseignantById(index: number, item: EnseignantMySuffix) {
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
    selector: 'jhi-eleve-my-suffix-popup',
    template: ''
})
export class EleveMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private elevePopupService: EleveMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.elevePopupService
                    .open(EleveMySuffixDialogComponent as Component, params['id']);
            } else {
                this.elevePopupService
                    .open(EleveMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

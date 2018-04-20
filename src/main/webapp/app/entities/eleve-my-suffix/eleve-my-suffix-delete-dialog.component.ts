import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EleveMySuffix } from './eleve-my-suffix.model';
import { EleveMySuffixPopupService } from './eleve-my-suffix-popup.service';
import { EleveMySuffixService } from './eleve-my-suffix.service';

@Component({
    selector: 'jhi-eleve-my-suffix-delete-dialog',
    templateUrl: './eleve-my-suffix-delete-dialog.component.html'
})
export class EleveMySuffixDeleteDialogComponent {

    eleve: EleveMySuffix;

    constructor(
        private eleveService: EleveMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.eleveService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'eleveListModification',
                content: 'Deleted an eleve'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-eleve-my-suffix-delete-popup',
    template: ''
})
export class EleveMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private elevePopupService: EleveMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.elevePopupService
                .open(EleveMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PaiementMySuffix } from './paiement-my-suffix.model';
import { PaiementMySuffixPopupService } from './paiement-my-suffix-popup.service';
import { PaiementMySuffixService } from './paiement-my-suffix.service';

@Component({
    selector: 'jhi-paiement-my-suffix-delete-dialog',
    templateUrl: './paiement-my-suffix-delete-dialog.component.html'
})
export class PaiementMySuffixDeleteDialogComponent {

    paiement: PaiementMySuffix;

    constructor(
        private paiementService: PaiementMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paiementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'paiementListModification',
                content: 'Deleted an paiement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-paiement-my-suffix-delete-popup',
    template: ''
})
export class PaiementMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paiementPopupService: PaiementMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.paiementPopupService
                .open(PaiementMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

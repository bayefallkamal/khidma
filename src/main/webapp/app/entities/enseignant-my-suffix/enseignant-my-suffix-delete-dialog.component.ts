import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EnseignantMySuffix } from './enseignant-my-suffix.model';
import { EnseignantMySuffixPopupService } from './enseignant-my-suffix-popup.service';
import { EnseignantMySuffixService } from './enseignant-my-suffix.service';

@Component({
    selector: 'jhi-enseignant-my-suffix-delete-dialog',
    templateUrl: './enseignant-my-suffix-delete-dialog.component.html'
})
export class EnseignantMySuffixDeleteDialogComponent {

    enseignant: EnseignantMySuffix;

    constructor(
        private enseignantService: EnseignantMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.enseignantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'enseignantListModification',
                content: 'Deleted an enseignant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-enseignant-my-suffix-delete-popup',
    template: ''
})
export class EnseignantMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private enseignantPopupService: EnseignantMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.enseignantPopupService
                .open(EnseignantMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}

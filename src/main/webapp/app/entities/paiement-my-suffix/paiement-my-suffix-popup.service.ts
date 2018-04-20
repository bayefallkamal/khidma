import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { PaiementMySuffix } from './paiement-my-suffix.model';
import { PaiementMySuffixService } from './paiement-my-suffix.service';

@Injectable()
export class PaiementMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private paiementService: PaiementMySuffixService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.paiementService.find(id)
                    .subscribe((paiementResponse: HttpResponse<PaiementMySuffix>) => {
                        const paiement: PaiementMySuffix = paiementResponse.body;
                        paiement.datePaiement = this.datePipe
                            .transform(paiement.datePaiement, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.paiementModalRef(component, paiement);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.paiementModalRef(component, new PaiementMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    paiementModalRef(component: Component, paiement: PaiementMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.paiement = paiement;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}

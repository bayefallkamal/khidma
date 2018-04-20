import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { EleveMySuffix } from './eleve-my-suffix.model';
import { EleveMySuffixService } from './eleve-my-suffix.service';

@Injectable()
export class EleveMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private eleveService: EleveMySuffixService

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
                this.eleveService.find(id)
                    .subscribe((eleveResponse: HttpResponse<EleveMySuffix>) => {
                        const eleve: EleveMySuffix = eleveResponse.body;
                        eleve.dateNaiss = this.datePipe
                            .transform(eleve.dateNaiss, 'yyyy-MM-ddTHH:mm:ss');
                        eleve.dateEntree = this.datePipe
                            .transform(eleve.dateEntree, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.eleveModalRef(component, eleve);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.eleveModalRef(component, new EleveMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    eleveModalRef(component: Component, eleve: EleveMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.eleve = eleve;
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

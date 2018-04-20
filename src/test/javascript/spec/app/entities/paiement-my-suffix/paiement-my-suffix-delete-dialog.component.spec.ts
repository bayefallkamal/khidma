/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { KhidmaTestModule } from '../../../test.module';
import { PaiementMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/paiement-my-suffix/paiement-my-suffix-delete-dialog.component';
import { PaiementMySuffixService } from '../../../../../../main/webapp/app/entities/paiement-my-suffix/paiement-my-suffix.service';

describe('Component Tests', () => {

    describe('PaiementMySuffix Management Delete Component', () => {
        let comp: PaiementMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<PaiementMySuffixDeleteDialogComponent>;
        let service: PaiementMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhidmaTestModule],
                declarations: [PaiementMySuffixDeleteDialogComponent],
                providers: [
                    PaiementMySuffixService
                ]
            })
            .overrideTemplate(PaiementMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaiementMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaiementMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { KhidmaTestModule } from '../../../test.module';
import { EleveMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/eleve-my-suffix/eleve-my-suffix-delete-dialog.component';
import { EleveMySuffixService } from '../../../../../../main/webapp/app/entities/eleve-my-suffix/eleve-my-suffix.service';

describe('Component Tests', () => {

    describe('EleveMySuffix Management Delete Component', () => {
        let comp: EleveMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<EleveMySuffixDeleteDialogComponent>;
        let service: EleveMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhidmaTestModule],
                declarations: [EleveMySuffixDeleteDialogComponent],
                providers: [
                    EleveMySuffixService
                ]
            })
            .overrideTemplate(EleveMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EleveMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EleveMySuffixService);
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

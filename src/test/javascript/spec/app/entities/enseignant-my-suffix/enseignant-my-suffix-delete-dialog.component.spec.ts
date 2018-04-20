/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { KhidmaTestModule } from '../../../test.module';
import { EnseignantMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/enseignant-my-suffix/enseignant-my-suffix-delete-dialog.component';
import { EnseignantMySuffixService } from '../../../../../../main/webapp/app/entities/enseignant-my-suffix/enseignant-my-suffix.service';

describe('Component Tests', () => {

    describe('EnseignantMySuffix Management Delete Component', () => {
        let comp: EnseignantMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<EnseignantMySuffixDeleteDialogComponent>;
        let service: EnseignantMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhidmaTestModule],
                declarations: [EnseignantMySuffixDeleteDialogComponent],
                providers: [
                    EnseignantMySuffixService
                ]
            })
            .overrideTemplate(EnseignantMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EnseignantMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnseignantMySuffixService);
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

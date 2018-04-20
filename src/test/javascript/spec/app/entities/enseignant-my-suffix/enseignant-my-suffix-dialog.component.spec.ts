/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { KhidmaTestModule } from '../../../test.module';
import { EnseignantMySuffixDialogComponent } from '../../../../../../main/webapp/app/entities/enseignant-my-suffix/enseignant-my-suffix-dialog.component';
import { EnseignantMySuffixService } from '../../../../../../main/webapp/app/entities/enseignant-my-suffix/enseignant-my-suffix.service';
import { EnseignantMySuffix } from '../../../../../../main/webapp/app/entities/enseignant-my-suffix/enseignant-my-suffix.model';
import { EleveMySuffixService } from '../../../../../../main/webapp/app/entities/eleve-my-suffix';

describe('Component Tests', () => {

    describe('EnseignantMySuffix Management Dialog Component', () => {
        let comp: EnseignantMySuffixDialogComponent;
        let fixture: ComponentFixture<EnseignantMySuffixDialogComponent>;
        let service: EnseignantMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhidmaTestModule],
                declarations: [EnseignantMySuffixDialogComponent],
                providers: [
                    EleveMySuffixService,
                    EnseignantMySuffixService
                ]
            })
            .overrideTemplate(EnseignantMySuffixDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EnseignantMySuffixDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnseignantMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EnseignantMySuffix(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.enseignant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'enseignantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EnseignantMySuffix();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.enseignant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'enseignantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});

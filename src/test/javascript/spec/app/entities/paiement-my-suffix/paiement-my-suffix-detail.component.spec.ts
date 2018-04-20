/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { KhidmaTestModule } from '../../../test.module';
import { PaiementMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/paiement-my-suffix/paiement-my-suffix-detail.component';
import { PaiementMySuffixService } from '../../../../../../main/webapp/app/entities/paiement-my-suffix/paiement-my-suffix.service';
import { PaiementMySuffix } from '../../../../../../main/webapp/app/entities/paiement-my-suffix/paiement-my-suffix.model';

describe('Component Tests', () => {

    describe('PaiementMySuffix Management Detail Component', () => {
        let comp: PaiementMySuffixDetailComponent;
        let fixture: ComponentFixture<PaiementMySuffixDetailComponent>;
        let service: PaiementMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhidmaTestModule],
                declarations: [PaiementMySuffixDetailComponent],
                providers: [
                    PaiementMySuffixService
                ]
            })
            .overrideTemplate(PaiementMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaiementMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaiementMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PaiementMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.paiement).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

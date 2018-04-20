/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KhidmaTestModule } from '../../../test.module';
import { PaiementMySuffixComponent } from '../../../../../../main/webapp/app/entities/paiement-my-suffix/paiement-my-suffix.component';
import { PaiementMySuffixService } from '../../../../../../main/webapp/app/entities/paiement-my-suffix/paiement-my-suffix.service';
import { PaiementMySuffix } from '../../../../../../main/webapp/app/entities/paiement-my-suffix/paiement-my-suffix.model';

describe('Component Tests', () => {

    describe('PaiementMySuffix Management Component', () => {
        let comp: PaiementMySuffixComponent;
        let fixture: ComponentFixture<PaiementMySuffixComponent>;
        let service: PaiementMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhidmaTestModule],
                declarations: [PaiementMySuffixComponent],
                providers: [
                    PaiementMySuffixService
                ]
            })
            .overrideTemplate(PaiementMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaiementMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaiementMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PaiementMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.paiements[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

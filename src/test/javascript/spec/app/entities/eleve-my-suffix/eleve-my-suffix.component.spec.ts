/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KhidmaTestModule } from '../../../test.module';
import { EleveMySuffixComponent } from '../../../../../../main/webapp/app/entities/eleve-my-suffix/eleve-my-suffix.component';
import { EleveMySuffixService } from '../../../../../../main/webapp/app/entities/eleve-my-suffix/eleve-my-suffix.service';
import { EleveMySuffix } from '../../../../../../main/webapp/app/entities/eleve-my-suffix/eleve-my-suffix.model';

describe('Component Tests', () => {

    describe('EleveMySuffix Management Component', () => {
        let comp: EleveMySuffixComponent;
        let fixture: ComponentFixture<EleveMySuffixComponent>;
        let service: EleveMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhidmaTestModule],
                declarations: [EleveMySuffixComponent],
                providers: [
                    EleveMySuffixService
                ]
            })
            .overrideTemplate(EleveMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EleveMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EleveMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EleveMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.eleves[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

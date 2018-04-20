/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { KhidmaTestModule } from '../../../test.module';
import { EleveMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/eleve-my-suffix/eleve-my-suffix-detail.component';
import { EleveMySuffixService } from '../../../../../../main/webapp/app/entities/eleve-my-suffix/eleve-my-suffix.service';
import { EleveMySuffix } from '../../../../../../main/webapp/app/entities/eleve-my-suffix/eleve-my-suffix.model';

describe('Component Tests', () => {

    describe('EleveMySuffix Management Detail Component', () => {
        let comp: EleveMySuffixDetailComponent;
        let fixture: ComponentFixture<EleveMySuffixDetailComponent>;
        let service: EleveMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhidmaTestModule],
                declarations: [EleveMySuffixDetailComponent],
                providers: [
                    EleveMySuffixService
                ]
            })
            .overrideTemplate(EleveMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EleveMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EleveMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EleveMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.eleve).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

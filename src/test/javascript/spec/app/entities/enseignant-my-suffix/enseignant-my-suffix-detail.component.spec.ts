/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { KhidmaTestModule } from '../../../test.module';
import { EnseignantMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/enseignant-my-suffix/enseignant-my-suffix-detail.component';
import { EnseignantMySuffixService } from '../../../../../../main/webapp/app/entities/enseignant-my-suffix/enseignant-my-suffix.service';
import { EnseignantMySuffix } from '../../../../../../main/webapp/app/entities/enseignant-my-suffix/enseignant-my-suffix.model';

describe('Component Tests', () => {

    describe('EnseignantMySuffix Management Detail Component', () => {
        let comp: EnseignantMySuffixDetailComponent;
        let fixture: ComponentFixture<EnseignantMySuffixDetailComponent>;
        let service: EnseignantMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhidmaTestModule],
                declarations: [EnseignantMySuffixDetailComponent],
                providers: [
                    EnseignantMySuffixService
                ]
            })
            .overrideTemplate(EnseignantMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EnseignantMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnseignantMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EnseignantMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.enseignant).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

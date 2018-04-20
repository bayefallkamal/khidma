/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KhidmaTestModule } from '../../../test.module';
import { EnseignantMySuffixComponent } from '../../../../../../main/webapp/app/entities/enseignant-my-suffix/enseignant-my-suffix.component';
import { EnseignantMySuffixService } from '../../../../../../main/webapp/app/entities/enseignant-my-suffix/enseignant-my-suffix.service';
import { EnseignantMySuffix } from '../../../../../../main/webapp/app/entities/enseignant-my-suffix/enseignant-my-suffix.model';

describe('Component Tests', () => {

    describe('EnseignantMySuffix Management Component', () => {
        let comp: EnseignantMySuffixComponent;
        let fixture: ComponentFixture<EnseignantMySuffixComponent>;
        let service: EnseignantMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhidmaTestModule],
                declarations: [EnseignantMySuffixComponent],
                providers: [
                    EnseignantMySuffixService
                ]
            })
            .overrideTemplate(EnseignantMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EnseignantMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnseignantMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EnseignantMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.enseignants[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

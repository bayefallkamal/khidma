/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { KhidmaTestModule } from '../../../test.module';
import { ParentMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/parent-my-suffix/parent-my-suffix-detail.component';
import { ParentMySuffixService } from '../../../../../../main/webapp/app/entities/parent-my-suffix/parent-my-suffix.service';
import { ParentMySuffix } from '../../../../../../main/webapp/app/entities/parent-my-suffix/parent-my-suffix.model';

describe('Component Tests', () => {

    describe('ParentMySuffix Management Detail Component', () => {
        let comp: ParentMySuffixDetailComponent;
        let fixture: ComponentFixture<ParentMySuffixDetailComponent>;
        let service: ParentMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhidmaTestModule],
                declarations: [ParentMySuffixDetailComponent],
                providers: [
                    ParentMySuffixService
                ]
            })
            .overrideTemplate(ParentMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParentMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParentMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ParentMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.parent).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

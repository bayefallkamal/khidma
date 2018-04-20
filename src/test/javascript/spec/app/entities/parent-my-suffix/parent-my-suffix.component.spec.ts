/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KhidmaTestModule } from '../../../test.module';
import { ParentMySuffixComponent } from '../../../../../../main/webapp/app/entities/parent-my-suffix/parent-my-suffix.component';
import { ParentMySuffixService } from '../../../../../../main/webapp/app/entities/parent-my-suffix/parent-my-suffix.service';
import { ParentMySuffix } from '../../../../../../main/webapp/app/entities/parent-my-suffix/parent-my-suffix.model';

describe('Component Tests', () => {

    describe('ParentMySuffix Management Component', () => {
        let comp: ParentMySuffixComponent;
        let fixture: ComponentFixture<ParentMySuffixComponent>;
        let service: ParentMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KhidmaTestModule],
                declarations: [ParentMySuffixComponent],
                providers: [
                    ParentMySuffixService
                ]
            })
            .overrideTemplate(ParentMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParentMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParentMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ParentMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.parents[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});

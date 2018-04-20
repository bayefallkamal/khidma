import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KhidmaSharedModule } from '../../shared';
import {
    EleveMySuffixService,
    EleveMySuffixPopupService,
    EleveMySuffixComponent,
    EleveMySuffixDetailComponent,
    EleveMySuffixDialogComponent,
    EleveMySuffixPopupComponent,
    EleveMySuffixDeletePopupComponent,
    EleveMySuffixDeleteDialogComponent,
    eleveRoute,
    elevePopupRoute,
} from './';

const ENTITY_STATES = [
    ...eleveRoute,
    ...elevePopupRoute,
];

@NgModule({
    imports: [
        KhidmaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EleveMySuffixComponent,
        EleveMySuffixDetailComponent,
        EleveMySuffixDialogComponent,
        EleveMySuffixDeleteDialogComponent,
        EleveMySuffixPopupComponent,
        EleveMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        EleveMySuffixComponent,
        EleveMySuffixDialogComponent,
        EleveMySuffixPopupComponent,
        EleveMySuffixDeleteDialogComponent,
        EleveMySuffixDeletePopupComponent,
    ],
    providers: [
        EleveMySuffixService,
        EleveMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhidmaEleveMySuffixModule {}

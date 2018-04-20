import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KhidmaSharedModule } from '../../shared';
import {
    ParentMySuffixService,
    ParentMySuffixPopupService,
    ParentMySuffixComponent,
    ParentMySuffixDetailComponent,
    ParentMySuffixDialogComponent,
    ParentMySuffixPopupComponent,
    ParentMySuffixDeletePopupComponent,
    ParentMySuffixDeleteDialogComponent,
    parentRoute,
    parentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...parentRoute,
    ...parentPopupRoute,
];

@NgModule({
    imports: [
        KhidmaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ParentMySuffixComponent,
        ParentMySuffixDetailComponent,
        ParentMySuffixDialogComponent,
        ParentMySuffixDeleteDialogComponent,
        ParentMySuffixPopupComponent,
        ParentMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ParentMySuffixComponent,
        ParentMySuffixDialogComponent,
        ParentMySuffixPopupComponent,
        ParentMySuffixDeleteDialogComponent,
        ParentMySuffixDeletePopupComponent,
    ],
    providers: [
        ParentMySuffixService,
        ParentMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhidmaParentMySuffixModule {}

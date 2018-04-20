import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KhidmaSharedModule } from '../../shared';
import {
    EnseignantMySuffixService,
    EnseignantMySuffixPopupService,
    EnseignantMySuffixComponent,
    EnseignantMySuffixDetailComponent,
    EnseignantMySuffixDialogComponent,
    EnseignantMySuffixPopupComponent,
    EnseignantMySuffixDeletePopupComponent,
    EnseignantMySuffixDeleteDialogComponent,
    enseignantRoute,
    enseignantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...enseignantRoute,
    ...enseignantPopupRoute,
];

@NgModule({
    imports: [
        KhidmaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EnseignantMySuffixComponent,
        EnseignantMySuffixDetailComponent,
        EnseignantMySuffixDialogComponent,
        EnseignantMySuffixDeleteDialogComponent,
        EnseignantMySuffixPopupComponent,
        EnseignantMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        EnseignantMySuffixComponent,
        EnseignantMySuffixDialogComponent,
        EnseignantMySuffixPopupComponent,
        EnseignantMySuffixDeleteDialogComponent,
        EnseignantMySuffixDeletePopupComponent,
    ],
    providers: [
        EnseignantMySuffixService,
        EnseignantMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhidmaEnseignantMySuffixModule {}

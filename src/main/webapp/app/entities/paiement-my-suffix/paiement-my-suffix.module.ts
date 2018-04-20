import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KhidmaSharedModule } from '../../shared';
import {
    PaiementMySuffixService,
    PaiementMySuffixPopupService,
    PaiementMySuffixComponent,
    PaiementMySuffixDetailComponent,
    PaiementMySuffixDialogComponent,
    PaiementMySuffixPopupComponent,
    PaiementMySuffixDeletePopupComponent,
    PaiementMySuffixDeleteDialogComponent,
    paiementRoute,
    paiementPopupRoute,
} from './';

const ENTITY_STATES = [
    ...paiementRoute,
    ...paiementPopupRoute,
];

@NgModule({
    imports: [
        KhidmaSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PaiementMySuffixComponent,
        PaiementMySuffixDetailComponent,
        PaiementMySuffixDialogComponent,
        PaiementMySuffixDeleteDialogComponent,
        PaiementMySuffixPopupComponent,
        PaiementMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        PaiementMySuffixComponent,
        PaiementMySuffixDialogComponent,
        PaiementMySuffixPopupComponent,
        PaiementMySuffixDeleteDialogComponent,
        PaiementMySuffixDeletePopupComponent,
    ],
    providers: [
        PaiementMySuffixService,
        PaiementMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhidmaPaiementMySuffixModule {}

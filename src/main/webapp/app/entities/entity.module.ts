import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { KhidmaRegionMySuffixModule } from './region-my-suffix/region-my-suffix.module';
import { KhidmaCountryMySuffixModule } from './country-my-suffix/country-my-suffix.module';
import { KhidmaLocationMySuffixModule } from './location-my-suffix/location-my-suffix.module';
import { KhidmaDepartmentMySuffixModule } from './department-my-suffix/department-my-suffix.module';
import { KhidmaTaskMySuffixModule } from './task-my-suffix/task-my-suffix.module';
import { KhidmaEmployeeMySuffixModule } from './employee-my-suffix/employee-my-suffix.module';
import { KhidmaEleveMySuffixModule } from './eleve-my-suffix/eleve-my-suffix.module';
import { KhidmaParentMySuffixModule } from './parent-my-suffix/parent-my-suffix.module';
import { KhidmaPaiementMySuffixModule } from './paiement-my-suffix/paiement-my-suffix.module';
import { KhidmaEnseignantMySuffixModule } from './enseignant-my-suffix/enseignant-my-suffix.module';
import { KhidmaJobMySuffixModule } from './job-my-suffix/job-my-suffix.module';
import { KhidmaJobHistoryMySuffixModule } from './job-history-my-suffix/job-history-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        KhidmaRegionMySuffixModule,
        KhidmaCountryMySuffixModule,
        KhidmaLocationMySuffixModule,
        KhidmaDepartmentMySuffixModule,
        KhidmaTaskMySuffixModule,
        KhidmaEmployeeMySuffixModule,
        KhidmaEleveMySuffixModule,
        KhidmaParentMySuffixModule,
        KhidmaPaiementMySuffixModule,
        KhidmaEnseignantMySuffixModule,
        KhidmaJobMySuffixModule,
        KhidmaJobHistoryMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KhidmaEntityModule {}

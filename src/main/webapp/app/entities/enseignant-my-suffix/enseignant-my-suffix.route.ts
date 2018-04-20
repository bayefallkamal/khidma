import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EnseignantMySuffixComponent } from './enseignant-my-suffix.component';
import { EnseignantMySuffixDetailComponent } from './enseignant-my-suffix-detail.component';
import { EnseignantMySuffixPopupComponent } from './enseignant-my-suffix-dialog.component';
import { EnseignantMySuffixDeletePopupComponent } from './enseignant-my-suffix-delete-dialog.component';

export const enseignantRoute: Routes = [
    {
        path: 'enseignant-my-suffix',
        component: EnseignantMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khidmaApp.enseignant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'enseignant-my-suffix/:id',
        component: EnseignantMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khidmaApp.enseignant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const enseignantPopupRoute: Routes = [
    {
        path: 'enseignant-my-suffix-new',
        component: EnseignantMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khidmaApp.enseignant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'enseignant-my-suffix/:id/edit',
        component: EnseignantMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khidmaApp.enseignant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'enseignant-my-suffix/:id/delete',
        component: EnseignantMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khidmaApp.enseignant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

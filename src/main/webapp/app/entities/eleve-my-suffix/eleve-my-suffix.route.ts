import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EleveMySuffixComponent } from './eleve-my-suffix.component';
import { EleveMySuffixDetailComponent } from './eleve-my-suffix-detail.component';
import { EleveMySuffixPopupComponent } from './eleve-my-suffix-dialog.component';
import { EleveMySuffixDeletePopupComponent } from './eleve-my-suffix-delete-dialog.component';

export const eleveRoute: Routes = [
    {
        path: 'eleve-my-suffix',
        component: EleveMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khidmaApp.eleve.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'eleve-my-suffix/:id',
        component: EleveMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khidmaApp.eleve.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const elevePopupRoute: Routes = [
    {
        path: 'eleve-my-suffix-new',
        component: EleveMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khidmaApp.eleve.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'eleve-my-suffix/:id/edit',
        component: EleveMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khidmaApp.eleve.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'eleve-my-suffix/:id/delete',
        component: EleveMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khidmaApp.eleve.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

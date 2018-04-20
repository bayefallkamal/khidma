import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaiementMySuffixComponent } from './paiement-my-suffix.component';
import { PaiementMySuffixDetailComponent } from './paiement-my-suffix-detail.component';
import { PaiementMySuffixPopupComponent } from './paiement-my-suffix-dialog.component';
import { PaiementMySuffixDeletePopupComponent } from './paiement-my-suffix-delete-dialog.component';

export const paiementRoute: Routes = [
    {
        path: 'paiement-my-suffix',
        component: PaiementMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khidmaApp.paiement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'paiement-my-suffix/:id',
        component: PaiementMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khidmaApp.paiement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paiementPopupRoute: Routes = [
    {
        path: 'paiement-my-suffix-new',
        component: PaiementMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khidmaApp.paiement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'paiement-my-suffix/:id/edit',
        component: PaiementMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khidmaApp.paiement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'paiement-my-suffix/:id/delete',
        component: PaiementMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'khidmaApp.paiement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

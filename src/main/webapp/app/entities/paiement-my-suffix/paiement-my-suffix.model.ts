import { BaseEntity } from './../../shared';

export class PaiementMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public datePaiement?: any,
        public montant?: number,
        public typePaiement?: string,
        public mois?: string,
        public nomPayeur?: string,
        public telPayeur?: string,
        public eleveId?: number,
    ) {
    }
}

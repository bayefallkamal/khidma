import { BaseEntity } from './../../shared';

export class EleveMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public dateNaiss?: any,
        public lieuNaiss?: string,
        public dateEntree?: any,
        public photoContentType?: string,
        public photo?: any,
        public parents?: BaseEntity[],
        public paiements?: BaseEntity[],
        public enseignants?: BaseEntity[],
    ) {
    }
}

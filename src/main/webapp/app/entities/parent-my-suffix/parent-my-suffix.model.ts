import { BaseEntity } from './../../shared';

export class ParentMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phoneNumber?: string,
        public adresse?: string,
        public eleveId?: number,
    ) {
    }
}

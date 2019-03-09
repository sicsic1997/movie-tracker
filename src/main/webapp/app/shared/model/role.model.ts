export interface IRole {
    id?: number;
    code?: string;
}

export class Role implements IRole {
    constructor(public id?: number, public code?: string) {}
}

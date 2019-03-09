export interface IProduction {
    id?: number;
    code?: string;
}

export class Production implements IProduction {
    constructor(public id?: number, public code?: string) {}
}

export interface IRated {
    id?: number;
    code?: string;
}

export class Rated implements IRated {
    constructor(public id?: number, public code?: string) {}
}

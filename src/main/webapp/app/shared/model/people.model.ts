export interface IPeople {
    id?: number;
    name?: string;
}

export class People implements IPeople {
    constructor(public id?: number, public name?: string) {}
}

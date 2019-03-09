export interface IGenre {
    id?: number;
    code?: string;
}

export class Genre implements IGenre {
    constructor(public id?: number, public code?: string) {}
}

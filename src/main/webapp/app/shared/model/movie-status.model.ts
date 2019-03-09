export interface IMovieStatus {
    id?: number;
    code?: string;
}

export class MovieStatus implements IMovieStatus {
    constructor(public id?: number, public code?: string) {}
}

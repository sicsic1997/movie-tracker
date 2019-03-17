export interface ISimilarity {
    id?: number;
    value?: number;
    movieAId?: number;
    movieBId?: number;
}

export class Similarity implements ISimilarity {
    constructor(public id?: number, public value?: number, public movieAId?: number, public movieBId?: number) {}
}

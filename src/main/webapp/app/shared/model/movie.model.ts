export interface IMovie {
    id?: number;
    title?: string;
    year?: number;
    released?: string;
    runtime?: number;
    plot?: string;
    poster?: string;
    rating?: number;
    ratedCode?: string;
    ratedId?: number;
    productionCode?: string;
    productionId?: number;
}

export class Movie implements IMovie {
    constructor(
        public id?: number,
        public title?: string,
        public year?: number,
        public released?: string,
        public runtime?: number,
        public plot?: string,
        public poster?: string,
        public rating?: number,
        public ratedCode?: string,
        public ratedId?: number,
        public productionCode?: string,
        public productionId?: number
    ) {}
}

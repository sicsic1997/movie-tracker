export interface IMovieLanguageMapping {
    id?: number;
    movieTitle?: string;
    movieId?: number;
    languageCode?: string;
    languageId?: number;
}

export class MovieLanguageMapping implements IMovieLanguageMapping {
    constructor(
        public id?: number,
        public movieTitle?: string,
        public movieId?: number,
        public languageCode?: string,
        public languageId?: number
    ) {}
}

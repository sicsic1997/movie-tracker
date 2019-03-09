export interface IMovieGenreMapping {
    id?: number;
    genreCode?: string;
    genreId?: number;
    movieTitle?: string;
    movieId?: number;
}

export class MovieGenreMapping implements IMovieGenreMapping {
    constructor(
        public id?: number,
        public genreCode?: string,
        public genreId?: number,
        public movieTitle?: string,
        public movieId?: number
    ) {}
}

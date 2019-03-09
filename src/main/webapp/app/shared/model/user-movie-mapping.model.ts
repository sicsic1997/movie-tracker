export interface IUserMovieMapping {
    id?: number;
    userLogin?: string;
    userId?: number;
    movieTitle?: string;
    movieId?: number;
    movieStatusCode?: string;
    movieStatusId?: number;
}

export class UserMovieMapping implements IUserMovieMapping {
    constructor(
        public id?: number,
        public userLogin?: string,
        public userId?: number,
        public movieTitle?: string,
        public movieId?: number,
        public movieStatusCode?: string,
        public movieStatusId?: number
    ) {}
}

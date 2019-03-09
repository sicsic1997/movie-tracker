export interface IMoviePeopleRoleMapping {
    id?: number;
    peopleName?: string;
    peopleId?: number;
    movieTitle?: string;
    movieId?: number;
    roleCode?: string;
    roleId?: number;
}

export class MoviePeopleRoleMapping implements IMoviePeopleRoleMapping {
    constructor(
        public id?: number,
        public peopleName?: string,
        public peopleId?: number,
        public movieTitle?: string,
        public movieId?: number,
        public roleCode?: string,
        public roleId?: number
    ) {}
}

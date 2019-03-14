import { IMovieLanguageMapping } from 'app/shared/model/movie-language-mapping.model';
import { IMovieGenreMapping } from 'app/shared/model/movie-genre-mapping.model';
import { IMoviePeopleRoleMapping } from 'app/shared/model/movie-people-role-mapping.model';

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
    languageLists?: IMovieLanguageMapping[];
    genreLists?: IMovieGenreMapping[];
    peopleLists?: IMoviePeopleRoleMapping[];
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
        public productionId?: number,
        public languageLists?: IMovieLanguageMapping[],
        public genreLists?: IMovieGenreMapping[],
        public peopleLists?: IMoviePeopleRoleMapping[]
    ) {}
}

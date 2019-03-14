import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'movie',
                loadChildren: './movie/movie.module#MovieTrackerMovieModule'
            },
            {
                path: 'people',
                loadChildren: './people/people.module#MovieTrackerPeopleModule'
            },
            {
                path: 'role',
                loadChildren: './role/role.module#MovieTrackerRoleModule'
            },
            {
                path: 'language',
                loadChildren: './language/language.module#MovieTrackerLanguageModule'
            },
            {
                path: 'production',
                loadChildren: './production/production.module#MovieTrackerProductionModule'
            },
            {
                path: 'rated',
                loadChildren: './rated/rated.module#MovieTrackerRatedModule'
            },
            {
                path: 'genre',
                loadChildren: './genre/genre.module#MovieTrackerGenreModule'
            },
            {
                path: 'movie-status',
                loadChildren: './movie-status/movie-status.module#MovieTrackerMovieStatusModule'
            },
            {
                path: 'user-movie-mapping',
                loadChildren: './user-movie-mapping/user-movie-mapping.module#MovieTrackerUserMovieMappingModule'
            },
            {
                path: 'movie-language-mapping',
                loadChildren: './movie-language-mapping/movie-language-mapping.module#MovieTrackerMovieLanguageMappingModule'
            },
            {
                path: 'movie-genre-mapping',
                loadChildren: './movie-genre-mapping/movie-genre-mapping.module#MovieTrackerMovieGenreMappingModule'
            },
            {
                path: 'movie-people-role-mapping',
                loadChildren: './movie-people-role-mapping/movie-people-role-mapping.module#MovieTrackerMoviePeopleRoleMappingModule'
            },
            {
                path: 'movie',
                loadChildren: './movie/movie.module#MovieTrackerMovieModule'
            },
            {
                path: 'people',
                loadChildren: './people/people.module#MovieTrackerPeopleModule'
            },
            {
                path: 'role',
                loadChildren: './role/role.module#MovieTrackerRoleModule'
            },
            {
                path: 'language',
                loadChildren: './language/language.module#MovieTrackerLanguageModule'
            },
            {
                path: 'production',
                loadChildren: './production/production.module#MovieTrackerProductionModule'
            },
            {
                path: 'rated',
                loadChildren: './rated/rated.module#MovieTrackerRatedModule'
            },
            {
                path: 'genre',
                loadChildren: './genre/genre.module#MovieTrackerGenreModule'
            },
            {
                path: 'movie-status',
                loadChildren: './movie-status/movie-status.module#MovieTrackerMovieStatusModule'
            },
            {
                path: 'user-movie-mapping',
                loadChildren: './user-movie-mapping/user-movie-mapping.module#MovieTrackerUserMovieMappingModule'
            },
            {
                path: 'movie-language-mapping',
                loadChildren: './movie-language-mapping/movie-language-mapping.module#MovieTrackerMovieLanguageMappingModule'
            },
            {
                path: 'movie-genre-mapping',
                loadChildren: './movie-genre-mapping/movie-genre-mapping.module#MovieTrackerMovieGenreMappingModule'
            },
            {
                path: 'movie-people-role-mapping',
                loadChildren: './movie-people-role-mapping/movie-people-role-mapping.module#MovieTrackerMoviePeopleRoleMappingModule'
            },
            {
                path: 'movie',
                loadChildren: './movie/movie.module#MovieTrackerMovieModule'
            },
            {
                path: 'movie',
                loadChildren: './movie/movie.module#MovieTrackerMovieModule'
            },
            {
                path: 'movie',
                loadChildren: './movie/movie.module#MovieTrackerMovieModule'
            },
            {
                path: 'movie',
                loadChildren: './movie/movie.module#MovieTrackerMovieModule'
            },
            {
                path: 'movie',
                loadChildren: './movie/movie.module#MovieTrackerMovieModule'
            },
            {
                path: 'movie-language-mapping',
                loadChildren: './movie-language-mapping/movie-language-mapping.module#MovieTrackerMovieLanguageMappingModule'
            },
            {
                path: 'movie-genre-mapping',
                loadChildren: './movie-genre-mapping/movie-genre-mapping.module#MovieTrackerMovieGenreMappingModule'
            },
            {
                path: 'movie-people-role-mapping',
                loadChildren: './movie-people-role-mapping/movie-people-role-mapping.module#MovieTrackerMoviePeopleRoleMappingModule'
            },
            {
                path: 'movie-language-mapping',
                loadChildren: './movie-language-mapping/movie-language-mapping.module#MovieTrackerMovieLanguageMappingModule'
            },
            {
                path: 'movie-genre-mapping',
                loadChildren: './movie-genre-mapping/movie-genre-mapping.module#MovieTrackerMovieGenreMappingModule'
            },
            {
                path: 'movie-people-role-mapping',
                loadChildren: './movie-people-role-mapping/movie-people-role-mapping.module#MovieTrackerMoviePeopleRoleMappingModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieTrackerEntityModule {}

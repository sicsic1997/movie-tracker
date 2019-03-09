import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MovieTrackerSharedModule } from 'app/shared';
import {
    GenreComponent,
    GenreDetailComponent,
    GenreUpdateComponent,
    GenreDeletePopupComponent,
    GenreDeleteDialogComponent,
    genreRoute,
    genrePopupRoute
} from './';

const ENTITY_STATES = [...genreRoute, ...genrePopupRoute];

@NgModule({
    imports: [MovieTrackerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [GenreComponent, GenreDetailComponent, GenreUpdateComponent, GenreDeleteDialogComponent, GenreDeletePopupComponent],
    entryComponents: [GenreComponent, GenreUpdateComponent, GenreDeleteDialogComponent, GenreDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieTrackerGenreModule {}

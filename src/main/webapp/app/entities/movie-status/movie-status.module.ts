import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MovieTrackerSharedModule } from 'app/shared';
import {
    MovieStatusComponent,
    MovieStatusDetailComponent,
    MovieStatusUpdateComponent,
    MovieStatusDeletePopupComponent,
    MovieStatusDeleteDialogComponent,
    movieStatusRoute,
    movieStatusPopupRoute
} from './';

const ENTITY_STATES = [...movieStatusRoute, ...movieStatusPopupRoute];

@NgModule({
    imports: [MovieTrackerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MovieStatusComponent,
        MovieStatusDetailComponent,
        MovieStatusUpdateComponent,
        MovieStatusDeleteDialogComponent,
        MovieStatusDeletePopupComponent
    ],
    entryComponents: [MovieStatusComponent, MovieStatusUpdateComponent, MovieStatusDeleteDialogComponent, MovieStatusDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieTrackerMovieStatusModule {}

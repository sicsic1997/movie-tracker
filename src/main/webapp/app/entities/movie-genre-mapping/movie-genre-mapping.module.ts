import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MovieTrackerSharedModule } from 'app/shared';
import {
    MovieGenreMappingComponent,
    MovieGenreMappingDetailComponent,
    MovieGenreMappingUpdateComponent,
    MovieGenreMappingDeletePopupComponent,
    MovieGenreMappingDeleteDialogComponent,
    movieGenreMappingRoute,
    movieGenreMappingPopupRoute
} from './';

const ENTITY_STATES = [...movieGenreMappingRoute, ...movieGenreMappingPopupRoute];

@NgModule({
    imports: [MovieTrackerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MovieGenreMappingComponent,
        MovieGenreMappingDetailComponent,
        MovieGenreMappingUpdateComponent,
        MovieGenreMappingDeleteDialogComponent,
        MovieGenreMappingDeletePopupComponent
    ],
    entryComponents: [
        MovieGenreMappingComponent,
        MovieGenreMappingUpdateComponent,
        MovieGenreMappingDeleteDialogComponent,
        MovieGenreMappingDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieTrackerMovieGenreMappingModule {}

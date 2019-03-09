import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MovieTrackerSharedModule } from 'app/shared';
import {
    MovieLanguageMappingComponent,
    MovieLanguageMappingDetailComponent,
    MovieLanguageMappingUpdateComponent,
    MovieLanguageMappingDeletePopupComponent,
    MovieLanguageMappingDeleteDialogComponent,
    movieLanguageMappingRoute,
    movieLanguageMappingPopupRoute
} from './';

const ENTITY_STATES = [...movieLanguageMappingRoute, ...movieLanguageMappingPopupRoute];

@NgModule({
    imports: [MovieTrackerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MovieLanguageMappingComponent,
        MovieLanguageMappingDetailComponent,
        MovieLanguageMappingUpdateComponent,
        MovieLanguageMappingDeleteDialogComponent,
        MovieLanguageMappingDeletePopupComponent
    ],
    entryComponents: [
        MovieLanguageMappingComponent,
        MovieLanguageMappingUpdateComponent,
        MovieLanguageMappingDeleteDialogComponent,
        MovieLanguageMappingDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieTrackerMovieLanguageMappingModule {}

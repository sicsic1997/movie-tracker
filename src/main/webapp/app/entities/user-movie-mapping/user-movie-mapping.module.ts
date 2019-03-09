import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MovieTrackerSharedModule } from 'app/shared';
import {
    UserMovieMappingComponent,
    UserMovieMappingDetailComponent,
    UserMovieMappingUpdateComponent,
    UserMovieMappingDeletePopupComponent,
    UserMovieMappingDeleteDialogComponent,
    userMovieMappingRoute,
    userMovieMappingPopupRoute
} from './';

const ENTITY_STATES = [...userMovieMappingRoute, ...userMovieMappingPopupRoute];

@NgModule({
    imports: [MovieTrackerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserMovieMappingComponent,
        UserMovieMappingDetailComponent,
        UserMovieMappingUpdateComponent,
        UserMovieMappingDeleteDialogComponent,
        UserMovieMappingDeletePopupComponent
    ],
    entryComponents: [
        UserMovieMappingComponent,
        UserMovieMappingUpdateComponent,
        UserMovieMappingDeleteDialogComponent,
        UserMovieMappingDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieTrackerUserMovieMappingModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MovieTrackerSharedModule } from 'app/shared';
import {
    MoviePeopleRoleMappingComponent,
    MoviePeopleRoleMappingDetailComponent,
    MoviePeopleRoleMappingUpdateComponent,
    MoviePeopleRoleMappingDeletePopupComponent,
    MoviePeopleRoleMappingDeleteDialogComponent,
    moviePeopleRoleMappingRoute,
    moviePeopleRoleMappingPopupRoute
} from './';

const ENTITY_STATES = [...moviePeopleRoleMappingRoute, ...moviePeopleRoleMappingPopupRoute];

@NgModule({
    imports: [MovieTrackerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MoviePeopleRoleMappingComponent,
        MoviePeopleRoleMappingDetailComponent,
        MoviePeopleRoleMappingUpdateComponent,
        MoviePeopleRoleMappingDeleteDialogComponent,
        MoviePeopleRoleMappingDeletePopupComponent
    ],
    entryComponents: [
        MoviePeopleRoleMappingComponent,
        MoviePeopleRoleMappingUpdateComponent,
        MoviePeopleRoleMappingDeleteDialogComponent,
        MoviePeopleRoleMappingDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieTrackerMoviePeopleRoleMappingModule {}

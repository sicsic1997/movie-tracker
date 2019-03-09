import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MovieTrackerSharedModule } from 'app/shared';
import {
    RatedComponent,
    RatedDetailComponent,
    RatedUpdateComponent,
    RatedDeletePopupComponent,
    RatedDeleteDialogComponent,
    ratedRoute,
    ratedPopupRoute
} from './';

const ENTITY_STATES = [...ratedRoute, ...ratedPopupRoute];

@NgModule({
    imports: [MovieTrackerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [RatedComponent, RatedDetailComponent, RatedUpdateComponent, RatedDeleteDialogComponent, RatedDeletePopupComponent],
    entryComponents: [RatedComponent, RatedUpdateComponent, RatedDeleteDialogComponent, RatedDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieTrackerRatedModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MovieTrackerSharedModule } from 'app/shared';
import {
    SimilarityComponent,
    SimilarityDetailComponent,
    SimilarityUpdateComponent,
    SimilarityDeletePopupComponent,
    SimilarityDeleteDialogComponent,
    similarityRoute,
    similarityPopupRoute
} from './';

const ENTITY_STATES = [...similarityRoute, ...similarityPopupRoute];

@NgModule({
    imports: [MovieTrackerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SimilarityComponent,
        SimilarityDetailComponent,
        SimilarityUpdateComponent,
        SimilarityDeleteDialogComponent,
        SimilarityDeletePopupComponent
    ],
    entryComponents: [SimilarityComponent, SimilarityUpdateComponent, SimilarityDeleteDialogComponent, SimilarityDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieTrackerSimilarityModule {}

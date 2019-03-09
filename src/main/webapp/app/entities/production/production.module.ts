import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MovieTrackerSharedModule } from 'app/shared';
import {
    ProductionComponent,
    ProductionDetailComponent,
    ProductionUpdateComponent,
    ProductionDeletePopupComponent,
    ProductionDeleteDialogComponent,
    productionRoute,
    productionPopupRoute
} from './';

const ENTITY_STATES = [...productionRoute, ...productionPopupRoute];

@NgModule({
    imports: [MovieTrackerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductionComponent,
        ProductionDetailComponent,
        ProductionUpdateComponent,
        ProductionDeleteDialogComponent,
        ProductionDeletePopupComponent
    ],
    entryComponents: [ProductionComponent, ProductionUpdateComponent, ProductionDeleteDialogComponent, ProductionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieTrackerProductionModule {}

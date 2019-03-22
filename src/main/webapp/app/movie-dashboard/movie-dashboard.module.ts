import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { MovieTrackerSharedModule } from 'app/shared';
import { MovieDashboardComponent } from './movie-dashboard.component';
import { movieDashboardRoutes } from './movie-dashboard.route';

@NgModule({
    declarations: [MovieDashboardComponent],
    imports: [CommonModule, MovieTrackerSharedModule, RouterModule.forChild(movieDashboardRoutes)],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieTrackerMovieDashboardModule {}

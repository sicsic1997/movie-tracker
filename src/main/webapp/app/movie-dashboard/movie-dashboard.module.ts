import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { MovieTrackerSharedModule } from 'app/shared';
import { MovieDashboardComponent } from './movie-dashboard.component';
import { movieDashboardRoutes } from './movie-dashboard.route';
import { MovieDashboardDetailComponent } from 'app/movie-dashboard/movie-dashboard-detail.component';
import { CarouselModule } from 'primeng/carousel';

@NgModule({
    declarations: [MovieDashboardComponent, MovieDashboardDetailComponent],
    imports: [CommonModule, MovieTrackerSharedModule, RouterModule.forChild(movieDashboardRoutes), CarouselModule],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieTrackerMovieDashboardModule {}

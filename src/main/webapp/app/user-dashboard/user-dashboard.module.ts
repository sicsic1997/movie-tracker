import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { MovieTrackerSharedModule } from 'app/shared';
import { UserDashboardComponent } from './user-dashboard.component';
import { UserDashboardRoutes } from './user-dashboard.route';
import { CarouselModule } from 'primeng/carousel';

@NgModule({
    declarations: [UserDashboardComponent],
    imports: [CommonModule, MovieTrackerSharedModule, RouterModule.forChild(UserDashboardRoutes), CarouselModule],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MovieTrackerUserDashboardModule {}

import { NgModule } from '@angular/core';

import { MovieTrackerSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [MovieTrackerSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [MovieTrackerSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class MovieTrackerSharedCommonModule {}

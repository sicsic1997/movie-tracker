import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRated } from 'app/shared/model/rated.model';

@Component({
    selector: 'jhi-rated-detail',
    templateUrl: './rated-detail.component.html'
})
export class RatedDetailComponent implements OnInit {
    rated: IRated;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rated }) => {
            this.rated = rated;
        });
    }

    previousState() {
        window.history.back();
    }
}

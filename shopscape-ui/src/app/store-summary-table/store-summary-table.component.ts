import { Component, Input } from '@angular/core';
import { StoreSummary } from '../data/Entities';
import {RouterLink} from '@angular/router';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-store-summary-table',
  imports: [
    RouterLink,
    NgForOf,
    NgIf
  ],
  templateUrl: './store-summary-table.component.html'
})
export class StoreSummaryTableComponent {
  @Input() summaries: StoreSummary[] = [];
}

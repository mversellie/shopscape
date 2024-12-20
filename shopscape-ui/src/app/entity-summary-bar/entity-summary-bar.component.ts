import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-entity-summary-bar',
  templateUrl: './entity-summary-bar.component.html'
})
export class EntitySummaryBarComponent {
  @Input() requests: number = 0;
  @Input() issues: number = 0;
}


import { Component } from '@angular/core';
import {EntitySummaryBarComponent} from '../entity-summary-bar/entity-summary-bar.component';
import {RequestService} from '../services/request.service';
import {IssueService} from '../services/issue.service';

@Component({
  selector: 'app-home-page',
  imports: [
    EntitySummaryBarComponent
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {
  requests: number = 0;
  issues: number = 0;

  constructor(private requestService: RequestService, private issueService: IssueService) {
    this.fetchCounts();
  }

  async fetchCounts() {
    this.requests = await this.requestService.getTotalRequests().then(data => data.count);
    this.issues = await this.issueService.getTotalIssues().then(data => data.count);
  }
}

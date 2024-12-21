
import {Component, OnInit} from '@angular/core';
import {EntitySummaryBarComponent} from '../entity-summary-bar/entity-summary-bar.component';
import {RequestService} from '../services/request.service';
import {IssueService} from '../services/issue.service';
import {RequestTableComponent} from '../request-table/request-table.component';
import {ShopScapeRequest} from '../data/ShopScapeRequest';
import {ShopScapeIssue} from '../data/ShopScapeIssue';
import {IssueTableComponent} from '../issue-table/issue-table.component';

@Component({
  selector: 'app-home-page',
  imports: [
    EntitySummaryBarComponent,
    RequestTableComponent,
    IssueTableComponent
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit {
  requestsCount: number = 0;
  issuesCount: number = 0;
  requests: ShopScapeRequest[] = [];
  issues: ShopScapeIssue[] = [];

  constructor(private requestService: RequestService, private issueService: IssueService) {
  }

  ngOnInit(): void {
    this.fetchCounts();
    this.fetchIssuesAndRequests()
  }

  async fetchCounts() {
    this.requestsCount = await this.requestService.getTotalRequests().then(data => data.count);
    this.issuesCount = await this.issueService.getTotalIssues().then(data => data.count);
  }

  async fetchIssuesAndRequests() {
    this.requests = await this.requestService.getAllRequests();
    this.issues = await this.issueService.getAllIssues()
  }
}

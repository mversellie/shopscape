import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IssueTableComponent } from '../issue-table/issue-table.component';
import { RequestTableComponent } from '../request-table/request-table.component';
import { ShopScapeRequest } from '../data/ShopScapeRequest';
import { ShopScapeIssue } from '../data/ShopScapeIssue';
import { ShopScapeStore } from '../data/Entities';
import { RequestService } from '../services/request.service';
import { IssueService } from '../services/issue.service';

@Component({
  selector: 'app-store-page',
  imports: [
    IssueTableComponent,
    RequestTableComponent
  ],
  templateUrl: './store-page.component.html',
  styleUrl: './store-page.component.css'
})
export class StorePageComponent implements OnInit {
  requestsCount: number = 0;
  issuesCount: number = 0;
  requests: ShopScapeRequest[] = [];
  issues: ShopScapeIssue[] = [];
  store:ShopScapeStore = new ShopScapeStore('', '', '', '', '', '', '')

  constructor(
    private requestService: RequestService,
    private issueService: IssueService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.store = data["entityContent"]; // Access the resolved store data
      this.fetchCounts();
      this.fetchIssuesAndRequests();
    });
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

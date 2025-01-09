import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IssueTableComponent } from '../issue-table/issue-table.component';
import { RequestTableComponent } from '../request-table/request-table.component';
import { ShopScapeRequest } from '../data/ShopScapeRequest';
import { ShopScapeIssue } from '../data/ShopScapeIssue';
import {Equipment, ShopScapeStore} from '../data/Entities';
import {EquipmentTableComponent} from '../equipment-table/equipment-table.component';
import {ShopService} from '../services/shop.service';

@Component({
  selector: 'app-store-page',
  imports: [
    IssueTableComponent,
    RequestTableComponent,
    EquipmentTableComponent
  ],
  templateUrl: './store-page.component.html',
  styleUrl: './store-page.component.css'
})
export class StorePageComponent implements OnInit {
  requestsCount: number = 0;
  issuesCount: number = 0;
  requests: ShopScapeRequest[] = [];
  issues: ShopScapeIssue[] = [];
  equipment:Equipment[] = [];
  store:ShopScapeStore = new ShopScapeStore('', '', '', '', '', '', '')

  constructor(
    private shopService: ShopService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.store = data["entityContent"]; // Access the resolved store data
      console.log(this.store)
      this.equipment = this.store.equipment
      this.requests = this.shopService.flattenAllRequestsForStore(this.store)
      this.issues = this.shopService.flattenAllIssuesForStore(this.store)
      this.requestsCount = this.requests.length
      this.issuesCount = this.issues.length
    });
  }
}

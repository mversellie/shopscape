import {Component, Input} from '@angular/core';
import {NgForOf} from '@angular/common';
import {ShopScapeIssue} from '../data/ShopScapeIssue';

@Component({
  selector: 'app-issue-table',
  imports: [
    NgForOf
  ],
  templateUrl: './issue-table.component.html'
})
export class IssueTableComponent {
  @Input() issues: ShopScapeIssue[] = [];
}

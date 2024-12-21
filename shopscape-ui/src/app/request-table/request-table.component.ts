import {Component, Input} from '@angular/core';
import {NgForOf} from '@angular/common';
import {ShopScapeRequest} from '../data/ShopScapeRequest';

@Component({
  selector: 'app-request-table',
  imports: [
    NgForOf
  ],
  templateUrl: './request-table.component.html'
})
export class RequestTableComponent {
  @Input() requests: ShopScapeRequest[] = [];
}

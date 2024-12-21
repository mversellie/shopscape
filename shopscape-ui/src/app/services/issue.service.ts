import { Injectable } from '@angular/core';
import { NetworkService } from './network.service';
import {ShopScapeIssue} from '../data/ShopScapeIssue';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class IssueService {

  constructor(private networkService: NetworkService) { }

  async getTotalIssues(): Promise<{ count: number }> {
    const response = await this.networkService.get(environment["api-url"] +'/api/issues/total-issues');
    return await response.json();
  }
  async getAllIssues(): Promise<ShopScapeIssue[]> {
    const response = await this.networkService.get(environment["api-url"] +'/api/issues');
    return (await response.json())['issues'] as ShopScapeIssue[];
  }
}

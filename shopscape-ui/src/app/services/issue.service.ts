import { Injectable } from '@angular/core';
import { NetworkService } from './network.service'; // Assuming NetworkService is in the same directory

@Injectable({
  providedIn: 'root'
})
export class IssueService {

  constructor(private networkService: NetworkService) { }

  async getTotalIssues(): Promise<{ count: number }> {
    const response = await this.networkService.get('/api/issues/total-issues');
    const data = await response.json();
    return data;
  }
}

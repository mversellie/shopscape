import { Injectable } from '@angular/core';
import { NetworkService } from './network.service';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  constructor(private networkService: NetworkService) { }

  async getTotalRequests(): Promise<{ count: number }> {
    const response = await this.networkService.get('/api/requests/total-requests');
    const data = await response.json();
    return data;
  }
}

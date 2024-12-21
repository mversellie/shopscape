import { Injectable } from '@angular/core';
import { NetworkService } from './network.service';
import {ShopScapeRequest} from '../data/ShopScapeRequest';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  constructor(private networkService: NetworkService) { }

  async getTotalRequests(): Promise<{ count: number }> {
    const response = await this.networkService.get(environment["api-url"] + '/api/requests/total-requests');
    return await response.json();
  }
  async getAllRequests(): Promise<ShopScapeRequest[]> {
    const response = await this.networkService.get(environment["api-url"] +'/api/requests');
    return (await response.json())['requests'] as ShopScapeRequest[];
  }
}

import { Injectable } from '@angular/core';
import { NetworkService } from './network.service';
import {ShopScapeStore, StoreSummary} from '../data/Entities';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ShopService {
  shopData: ShopScapeStore[] = [];
  storeSummaries:StoreSummary[] = [];


  constructor(private networkService: NetworkService) {
    this.getShops();
  }

  async getShops() {
    // @ts-ignore
    const response = await this.networkService.get(environment["api-url"] + '/api/stores');
    const data = await response.json();
    this.shopData = data["stores"];
    console.log(this.shopData)
    this.shopData.forEach(thing => this.storeSummaries.push(this.summarizeStore(thing)))
  }

  summarizeStore(store: ShopScapeStore):StoreSummary{
    let equipmentIssueCount = 0;
    let equipmentRequestCount = 0;

    store.equipment.forEach(equip => {
      equipmentIssueCount += equip.issues.length
      equipmentRequestCount += equip.requests.length
    });

    return new StoreSummary(store.name,store.id,
      equipmentIssueCount + store.issues.length,
      equipmentRequestCount + store.requests.length)
  }

  async getShopById(id: string): Promise<ShopScapeStore> {
    const response = await this.networkService.get(environment["api-url"] + `/api/stores/${id}`);
    const data = await response.json();
    return data;
  }
}

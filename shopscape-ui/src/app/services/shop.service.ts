import {Injectable, signal, WritableSignal} from '@angular/core';
import { NetworkService } from './network.service';
import {ShopScapeStore, StoreSummary} from '../data/Entities';
import {environment} from '../../environments/environment';
import {ShopScapeIssue} from '../data/ShopScapeIssue';
import {ShopScapeRequest} from '../data/ShopScapeRequest';

@Injectable({
  providedIn: 'root'
})
export class ShopService {
  shopData: WritableSignal<ShopScapeStore[]> = signal([]);
  storeSummaries:WritableSignal<StoreSummary[]> = signal([]);
  requests:WritableSignal<ShopScapeRequest[]> = signal([]);
  issues:WritableSignal<ShopScapeIssue[]> = signal([]);


  constructor(private networkService: NetworkService) {
    this.getShops();
  }

  async getShops() {
    // @ts-ignore
    const response = await this.networkService.get(environment["api-url"] + '/api/stores');
    const data = await response.json();
    this.shopData.set(data["stores"]);
    const tempSummaries:StoreSummary[] = []
    this.shopData().forEach(thing => tempSummaries.push(this.summarizeStore(thing)))
    this.storeSummaries.set(tempSummaries)
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

  flattenAllRequestsForStore(store: ShopScapeStore){
    let requests = store.requests;
    store.equipment.forEach(equip => { requests.push(... equip.requests); });
    return requests;
  }

  flattenAllIssuesForStore(store: ShopScapeStore){
    let issues = store.issues;
    store.equipment.forEach(equip => { issues.push(... equip.issues); });
    return issues;
  }
}

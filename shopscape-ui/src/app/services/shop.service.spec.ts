import { TestBed } from '@angular/core/testing';
import { ShopService } from './shop.service';
import { NetworkService } from './network.service';
import { makeShopResponse } from '../mocks/ShopResponseMock';
import {StoreSummary} from '../data/Entities';

class MockNetworkService {
  get<T>(url: string): Promise<Response> {
    // @ts-ignore
    return Promise.resolve(new Response(JSON.stringify(makeShopResponse()))); // Return the mock data
  }
}

class MockSingleNetworkService {
  get<T>(url: string): Promise<Response> {
    // @ts-ignore
    return Promise.resolve(new Response(JSON.stringify(makeShopResponse()[0]))); // Return the mock data
  }
}

describe('ShopService', () => {
  let service: ShopService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [],
      providers: [
        ShopService,
        { provide: NetworkService, useClass: MockNetworkService }
      ]
    });

    service = TestBed.inject(ShopService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch shops from the API and populate shopData', async () => {
    await service.getShops().then(() => {
      console.log(service.shopData)
      expect(service.shopData).toEqual(makeShopResponse());
    });
  });

  it('should correctly summarize store data from mock', () => {
    const mockShops = makeShopResponse();
    const store = mockShops[0];

    const expectedSummary = new StoreSummary(
      'Shop A',
      '1',
      2, // 1 store issue + 1 equipment issue
      4 // 2 store requests + 2 equipment with 1 request
    );

    const actualSummary = service.summarizeStore(store);

    expect(actualSummary).toEqual(expectedSummary);
  });

});

describe('ShopService-single-response', () => {
  let service: ShopService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [],
      providers: [
        ShopService,
        { provide: MockSingleNetworkService, useClass: MockNetworkService }
      ]
    });

    service = TestBed.inject(ShopService);
  });


  it('should fetch a single shop by ID', async () => {
    const mockShopId = 'some-store-id'; // Replace with a sample ID

    const fetchedShop = await service.getShopById(mockShopId);
    expect(fetchedShop).toEqual(makeShopResponse()[0]); // Check against the mocked data
  });

});


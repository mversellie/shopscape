import { TestBed } from '@angular/core/testing';
import { RequestService } from './request.service';
import { NetworkService } from './network.service';
import {ShopScapeRequest} from '../data/ShopScapeRequest';

describe('RequestService', () => {
  let service: RequestService;
  let networkService: NetworkService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RequestService, NetworkService]
    });

    service = TestBed.inject(RequestService);
    networkService = TestBed.inject(NetworkService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get total requests', async () => {
    const mockResponse = { count: 456 };

    // Mock the NetworkService's get() method
    // @ts-ignore
    spyOn(networkService, 'get').and.returnValue(Promise.resolve({ ok: true, json: () => mockResponse }));

    const result = await service.getTotalRequests();

    expect(result).toEqual(mockResponse);
    expect(networkService.get).toHaveBeenCalledWith('/api/requests/total-requests');
  });

  it('should get all requests', async () => {
    // @ts-ignore
    const mockRequests: ShopScapeRequest[] =

      [
        // @ts-ignore
        { id: '1', name: 'Request 1', description: 'Desc 1', entityId: '123', status: 'Pending' },
        // @ts-ignore
        { id: '2', name: 'Request 2', description: 'Desc 2', entityId: '456', status: 'Completed' }
    ];

    // @ts-ignore
    spyOn(networkService, 'get').and.returnValue(Promise.resolve({ ok: true, json: () => ({ requests: mockRequests }) }));

    const result = await service.getAllRequests();

    expect(result).toEqual(mockRequests);
    expect(networkService.get).toHaveBeenCalledWith('/api/requests');
  });

});

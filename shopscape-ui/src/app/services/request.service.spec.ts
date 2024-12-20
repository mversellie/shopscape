import { TestBed } from '@angular/core/testing';
import { RequestService } from './request.service';
import { NetworkService } from './network.service';

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
});

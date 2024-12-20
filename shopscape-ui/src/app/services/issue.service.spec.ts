import { TestBed } from '@angular/core/testing';
import { IssueService } from './issue.service';
import { NetworkService } from './network.service';
import { of } from 'rxjs'; // Import 'of' from rxjs for mocking

describe('IssueService', () => {
  let service: IssueService;
  let networkService: NetworkService; // Inject the NetworkService directly

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [IssueService, NetworkService]
    });

    service = TestBed.inject(IssueService);
    networkService = TestBed.inject(NetworkService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get total issues', async () => {
    const mockResponse = { count: 123 };

    // Mock the NetworkService's get() method
    // @ts-ignore
    spyOn(networkService, 'get').and.returnValue(Promise.resolve({ ok: true, json: () => mockResponse }));

    const result = await service.getTotalIssues();

    expect(result).toEqual(mockResponse);
    expect(networkService.get).toHaveBeenCalledWith('/api/issues/total-issues');

  });
});


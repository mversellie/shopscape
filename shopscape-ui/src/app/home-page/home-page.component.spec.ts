import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomePageComponent } from './home-page.component';
import { RequestService } from '../services/request.service';
import { IssueService } from '../services/issue.service';

describe('HomePageComponent', () => {
  let component: HomePageComponent;
  let fixture: ComponentFixture<HomePageComponent>;
  let mockRequestService: jasmine.SpyObj<RequestService> = jasmine.createSpyObj('RequestService', ['getTotalRequests']);
  let mockIssueService: jasmine.SpyObj<IssueService> = jasmine.createSpyObj('IssueService', ['getTotalIssues']);

  beforeEach(async () => {
    mockRequestService = jasmine.createSpyObj('RequestService', ['getTotalRequests']);
    mockIssueService = jasmine.createSpyObj('IssueService', ['getTotalIssues']);

    await TestBed.configureTestingModule({
      declarations: [  ],
      providers: [
        { provide: RequestService, useValue: mockRequestService },
        { provide: IssueService, useValue: mockIssueService }
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(HomePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch request and issue counts on initialization', () => {
    // Mock the service responses
    mockRequestService.getTotalRequests.and.returnValue(Promise.resolve({ count: 10 }));
    mockIssueService.getTotalIssues.and.returnValue(Promise.resolve({ count: 5 }));

    // Trigger the initialization
    component.fetchCounts();

    component.ngOnInit();
    fixture.detectChanges();
    // Assert that the counts are set correctly
    fixture.detectChanges();
    expect(component.requestsCount).toBe(10);
    expect(component.issuesCount).toBe(5);

    // Assert that the services were called
    expect(mockRequestService.getTotalRequests).toHaveBeenCalled();
    expect(mockIssueService.getTotalIssues).toHaveBeenCalled();
    });


});

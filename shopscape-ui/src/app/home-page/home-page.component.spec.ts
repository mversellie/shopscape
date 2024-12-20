import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomePageComponent } from './home-page.component';
import { EntitySummaryBarComponent } from '../entity-summary-bar/entity-summary-bar.component';
import { RequestService } from '../services/request.service';
import { IssueService } from '../services/issue.service';
import { of } from 'rxjs';

describe('HomePageComponent', () => {
  let component: HomePageComponent;
  let fixture: ComponentFixture<HomePageComponent>;
  let mockRequestService: jasmine.SpyObj<RequestService>;
  let mockIssueService: jasmine.SpyObj<IssueService>;

  beforeEach(async () => {
    mockRequestService = jasmine.createSpyObj('RequestService', ['getTotalRequests']);
    mockIssueService = jasmine.createSpyObj('IssueService', ['getTotalIssues']);

    await TestBed.configureTestingModule({
      declarations: [ HomePageComponent, EntitySummaryBarComponent ],
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
    // @ts-ignore
    mockRequestService.getTotalRequests.and.returnValue(of({ count: 10 }));
    // @ts-ignore
    mockIssueService.getTotalIssues.and.returnValue(of({ count: 5 }));

    // Trigger the initialization
    component.fetchCounts();

    // Assert that the services were called
    expect(mockRequestService.getTotalRequests).toHaveBeenCalled();
    expect(mockIssueService.getTotalIssues).toHaveBeenCalled();

    // Assert that the counts are set correctly
    fixture.detectChanges();
    expect(component.requests).toBe(10);
    expect(component.issues).toBe(5);
  });
});

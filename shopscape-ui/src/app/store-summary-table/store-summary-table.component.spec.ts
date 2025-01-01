import { ComponentFixture, TestBed } from '@angular/core/testing';
import { StoreSummaryTableComponent } from './store-summary-table.component';
import { StoreSummary } from '../data/Entities';

describe('StoreSummaryTableComponent', () => {
  let component: StoreSummaryTableComponent;
  let fixture: ComponentFixture<StoreSummaryTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
    }).compileComponents();

    fixture = TestBed.createComponent(StoreSummaryTableComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display store summaries in a table', () => {
    const storeSummaries: StoreSummary[] = [
      { Name: 'Store A', ID: '1', IssueCount: 5, RequestCount: 2 },
      { Name: 'Store B', ID: '2', IssueCount: 10, RequestCount: 8 },
    ];
    component.summaries = storeSummaries;
    fixture.detectChanges();
    const tableRows = fixture.debugElement.nativeElement.querySelectorAll('tr');
    expect(tableRows.length).toBe(storeSummaries.length);
    // Check content of each row
    // @ts-ignore
    tableRows.forEach((row, index) => {
      const cells = row.querySelectorAll('td');
      expect(cells[0].textContent).toContain(storeSummaries[index].Name);
      expect(cells[1].textContent).toBe(storeSummaries[index].RequestCount.toString());
      expect(cells[2].textContent).toBe(storeSummaries[index].IssueCount.toString());
    });
  });

  it('should create links to store detail pages', () => {
    const storeSummaries: StoreSummary[] = [
      { Name: 'Store A', ID: '1', IssueCount: 5, RequestCount: 2 },
    ];
    component.summaries = storeSummaries;
    fixture.detectChanges();
    const linkElement = fixture.debugElement.nativeElement.querySelector('a');
    expect(linkElement.getAttribute('routerLink')).toBe('/stores/1');
  });
});

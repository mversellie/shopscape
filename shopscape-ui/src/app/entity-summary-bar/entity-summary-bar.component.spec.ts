import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EntitySummaryBarComponent } from './entity-summary-bar.component';

describe('EntitySummaryBarComponent', () => {
  let component: EntitySummaryBarComponent;
  let fixture: ComponentFixture<EntitySummaryBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EntitySummaryBarComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EntitySummaryBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should display requests and issues', () => {
    component.requests = 5;
    component.issues = 2;
    fixture.detectChanges();

    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('.requests').textContent).toContain('5');
    expect(compiled.querySelector('.issues').textContent).toContain('2');
  });
});

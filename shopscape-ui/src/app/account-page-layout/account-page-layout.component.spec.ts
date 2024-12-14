import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountPageLayoutComponent } from './account-page-layout.component';

describe('AccountPageLayoutComponent', () => {
  let component: AccountPageLayoutComponent;
  let fixture: ComponentFixture<AccountPageLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountPageLayoutComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountPageLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

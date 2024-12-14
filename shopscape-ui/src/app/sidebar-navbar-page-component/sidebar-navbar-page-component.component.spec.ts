import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarNavbarPageComponentComponent } from './sidebar-navbar-page-component.component';

describe('SidebarNavbarPageComponentComponent', () => {
  let component: SidebarNavbarPageComponentComponent;
  let fixture: ComponentFixture<SidebarNavbarPageComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SidebarNavbarPageComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SidebarNavbarPageComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

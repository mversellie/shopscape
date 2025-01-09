import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColoredSquareComponent } from './colored-square.component';

describe('ColoredSquareComponent', () => {
  let component: ColoredSquareComponent;
  let fixture: ComponentFixture<ColoredSquareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ColoredSquareComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ColoredSquareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

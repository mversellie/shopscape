import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StoreDescriptionComponent } from './store-description.component';

describe('StoreDescriptionComponent', () => {
  let component: StoreDescriptionComponent;
  let fixture: ComponentFixture<StoreDescriptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StoreDescriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display the image', () => {
    component.imageSrc = 'test-image.jpg';
    fixture.detectChanges();
    const imgElement = fixture.nativeElement.querySelector('img');
    expect(imgElement.src).toContain('test-image.jpg');
  });

  it('should display the name', () => {
    component.name = 'Test Place';
    fixture.detectChanges();
    const nameElement = fixture.nativeElement.querySelector('.name');
    expect(nameElement.textContent).toContain('Test Place');
  });

  it('should display the description', () => {
    component.description = 'This is a test description.';
    fixture.detectChanges();
    const descriptionElement = fixture.nativeElement.querySelector('.description');
    expect(descriptionElement.textContent).toContain('This is a test description.');
  });

  it('should display the address', () => {
    component.address = '123 Test Street\nCity, State, Zip';
    fixture.detectChanges();
    const addressElement = fixture.nativeElement.querySelector('.address');
    expect(addressElement.textContent).toContain('123 Test Street\nCity, State, Zip');
  });
});

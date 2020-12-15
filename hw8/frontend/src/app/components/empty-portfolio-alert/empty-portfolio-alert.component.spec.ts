import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmptyPortfolioAlertComponent } from './empty-portfolio-alert.component';

describe('EmptyPortfolioAlertComponent', () => {
  let component: EmptyPortfolioAlertComponent;
  let fixture: ComponentFixture<EmptyPortfolioAlertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmptyPortfolioAlertComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmptyPortfolioAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

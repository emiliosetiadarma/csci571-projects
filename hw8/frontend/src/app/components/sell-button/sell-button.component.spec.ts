import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellButtonComponent } from './sell-button.component';

describe('SellButtonComponent', () => {
  let component: SellButtonComponent;
  let fixture: ComponentFixture<SellButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SellButtonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SellButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

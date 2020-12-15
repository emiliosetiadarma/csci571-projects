import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmptyWatchlistAlertComponent } from './empty-watchlist-alert.component';

describe('EmptyWatchlistAlertComponent', () => {
  let component: EmptyWatchlistAlertComponent;
  let fixture: ComponentFixture<EmptyWatchlistAlertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmptyWatchlistAlertComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmptyWatchlistAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

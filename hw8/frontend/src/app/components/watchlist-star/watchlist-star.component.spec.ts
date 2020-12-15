import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WatchlistStarComponent } from './watchlist-star.component';

describe('WatchlistStarComponent', () => {
  let component: WatchlistStarComponent;
  let fixture: ComponentFixture<WatchlistStarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WatchlistStarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WatchlistStarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewsGroupComponent } from './news-group.component';

describe('NewsGroupComponent', () => {
  let component: NewsGroupComponent;
  let fixture: ComponentFixture<NewsGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewsGroupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewsGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

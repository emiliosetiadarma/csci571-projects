import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeArrowComponent } from './change-arrow.component';

describe('ChangeArrowComponent', () => {
  let component: ChangeArrowComponent;
  let fixture: ComponentFixture<ChangeArrowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangeArrowComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeArrowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

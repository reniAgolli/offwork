import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeavesCalendarViewComponent } from './leaves-calendar-view.component';

describe('LeavesCalendarViewComponent', () => {
  let component: LeavesCalendarViewComponent;
  let fixture: ComponentFixture<LeavesCalendarViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeavesCalendarViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeavesCalendarViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

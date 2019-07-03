import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeavesTableViewComponent } from './leaves-table-view.component';

describe('LeavesTableViewComponent', () => {
  let component: LeavesTableViewComponent;
  let fixture: ComponentFixture<LeavesTableViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeavesTableViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeavesTableViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

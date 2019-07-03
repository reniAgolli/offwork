import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CuLeavesComponent } from './cu-leaves.component';

describe('CuLeavesComponent', () => {
  let component: CuLeavesComponent;
  let fixture: ComponentFixture<CuLeavesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CuLeavesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CuLeavesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

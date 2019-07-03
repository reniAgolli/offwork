import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CuUsersComponent } from './cu-users.component';

describe('CuUsersComponent', () => {
  let component: CuUsersComponent;
  let fixture: ComponentFixture<CuUsersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CuUsersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CuUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

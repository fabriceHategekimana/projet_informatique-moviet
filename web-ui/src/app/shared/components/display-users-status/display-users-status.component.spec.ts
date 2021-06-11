import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayUsersStatusComponent } from './display-users-status.component';

describe('DisplayUsersStatusComponent', () => {
  let component: DisplayUsersStatusComponent;
  let fixture: ComponentFixture<DisplayUsersStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DisplayUsersStatusComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DisplayUsersStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

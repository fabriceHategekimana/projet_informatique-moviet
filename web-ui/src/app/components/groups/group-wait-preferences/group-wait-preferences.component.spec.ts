import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupWaitPreferencesComponent } from './group-wait-preferences.component';

describe('GroupWaitPreferencesComponent', () => {
  let component: GroupWaitPreferencesComponent;
  let fixture: ComponentFixture<GroupWaitPreferencesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupWaitPreferencesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupWaitPreferencesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

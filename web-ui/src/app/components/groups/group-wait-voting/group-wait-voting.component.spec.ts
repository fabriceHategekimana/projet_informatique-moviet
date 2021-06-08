import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupWaitVotingComponent } from './group-wait-voting.component';

describe('GroupWaitVotingComponent', () => {
  let component: GroupWaitVotingComponent;
  let fixture: ComponentFixture<GroupWaitVotingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupWaitVotingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupWaitVotingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

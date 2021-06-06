import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupWaitResultComponent } from './group-wait-result.component';

describe('GroupWaitResultComponent', () => {
  let component: GroupWaitResultComponent;
  let fixture: ComponentFixture<GroupWaitResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupWaitResultComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupWaitResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

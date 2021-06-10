import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupShowResultComponent } from './group-show-result.component';

describe('GroupShowResultComponent', () => {
  let component: GroupShowResultComponent;
  let fixture: ComponentFixture<GroupShowResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupShowResultComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupShowResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

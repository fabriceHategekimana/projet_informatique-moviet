import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupFindMatchComponent } from './group-find-match.component';

describe('GroupFindMatchComponent', () => {
  let component: GroupFindMatchComponent;
  let fixture: ComponentFixture<GroupFindMatchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupFindMatchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupFindMatchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

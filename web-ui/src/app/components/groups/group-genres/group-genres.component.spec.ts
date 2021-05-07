import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupGenresComponent } from './group-genres.component';

describe('GroupGenresComponent', () => {
  let component: GroupGenresComponent;
  let fixture: ComponentFixture<GroupGenresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupGenresComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupGenresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

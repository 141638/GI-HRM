import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TasklogListComponent } from './tasklog-list.component';

describe('TasklogListComponent', () => {
  let component: TasklogListComponent;
  let fixture: ComponentFixture<TasklogListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TasklogListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TasklogListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

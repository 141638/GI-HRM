import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TasklogLogComponent } from './tasklog-log.component';

describe('TasklogLogComponent', () => {
  let component: TasklogLogComponent;
  let fixture: ComponentFixture<TasklogLogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TasklogLogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TasklogLogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

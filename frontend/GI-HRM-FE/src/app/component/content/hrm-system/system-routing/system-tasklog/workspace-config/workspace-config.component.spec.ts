import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkspaceConfigComponent } from './workspace-config.component';

describe('WorkspaceConfigComponent', () => {
  let component: WorkspaceConfigComponent;
  let fixture: ComponentFixture<WorkspaceConfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WorkspaceConfigComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WorkspaceConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

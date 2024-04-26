import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemRoutingComponent } from './system-routing.component';

describe('SystemRoutingComponent', () => {
  let component: SystemRoutingComponent;
  let fixture: ComponentFixture<SystemRoutingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SystemRoutingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SystemRoutingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AbsenceRequestForm } from './absence-request-form';

describe('AbsenceRequestForm', () => {
  let component: AbsenceRequestForm;
  let fixture: ComponentFixture<AbsenceRequestForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AbsenceRequestForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AbsenceRequestForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

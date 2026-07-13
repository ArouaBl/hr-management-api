import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AbsenceRequestList } from './absence-request-list';

describe('AbsenceRequestList', () => {
  let component: AbsenceRequestList;
  let fixture: ComponentFixture<AbsenceRequestList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AbsenceRequestList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AbsenceRequestList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

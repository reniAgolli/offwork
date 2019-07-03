import { TestBed } from '@angular/core/testing';

import { UiMessagesService } from './ui-messages.service';

describe('UiMessagesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UiMessagesService = TestBed.get(UiMessagesService);
    expect(service).toBeTruthy();
  });
});

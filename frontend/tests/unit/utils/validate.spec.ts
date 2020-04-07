import {
  isValidDate,
  isValidEmail,
  isValidPhoneNumber,
  isValidTime,
  isValidZoomID,
} from '@/utils/validate';

describe('validate utils', () => {
  it('email validation', () => {
    let email = 'ime@domen.com';
    let valid = isValidEmail(email);
    expect(valid).toBe(true);

    email = 'ime.prezime@domen.com';
    valid = isValidEmail(email);
    expect(valid).toBe(true);

    email = 'ime1prezime2@domen.com';
    valid = isValidEmail(email);
    expect(valid).toBe(true);

    email = 'ime@domen';
    valid = isValidEmail(email);
    expect(valid).toBe(false);

    email = 'ime.prezime.domen.com';
    valid = isValidEmail(email);
    expect(valid).toBe(false);
  });

  it('date validation', () => {
    let dateString = '2020/04/05';
    let valid = isValidDate(dateString);
    expect(valid).toBe(true);

    dateString = '2020/4/05';
    valid = isValidDate(dateString);
    expect(valid).toBe(true);

    dateString = '2020/4/5';
    valid = isValidDate(dateString);
    expect(valid).toBe(true);

    dateString = '2020/04/5';
    valid = isValidDate(dateString);
    expect(valid).toBe(true);

    dateString = '2020/10/30';
    valid = isValidDate(dateString);
    expect(valid).toBe(true);

    dateString = '2020/42/05';
    valid = isValidDate(dateString);
    expect(valid).toBe(false);

    dateString = '2020/4/34';
    valid = isValidDate(dateString);
    expect(valid).toBe(false);

    dateString = '2020/0/5';
    valid = isValidDate(dateString);
    expect(valid).toBe(false);

    dateString = '350/10/5';
    valid = isValidDate(dateString);
    expect(valid).toBe(false);

    dateString = '3000/02/02';
    valid = isValidDate(dateString);
    expect(valid).toBe(false);
  });

  it('time validation', () => {
    let timeString = '10:30';
    let valid = isValidTime(timeString);
    expect(valid).toBe(true);

    timeString = '10:3';
    valid = isValidTime(timeString);
    expect(valid).toBe(true);

    timeString = '10:03';
    valid = isValidTime(timeString);
    expect(valid).toBe(true);

    timeString = '1:30';
    valid = isValidTime(timeString);
    expect(valid).toBe(true);

    timeString = '01:30';
    valid = isValidTime(timeString);
    expect(valid).toBe(true);

    timeString = '18:45';
    valid = isValidTime(timeString);
    expect(valid).toBe(true);

    timeString = '22:09';
    valid = isValidTime(timeString);
    expect(valid).toBe(true);

    timeString = '0:30';
    valid = isValidTime(timeString);
    expect(valid).toBe(true);

    timeString = '24:10';
    valid = isValidTime(timeString);
    expect(valid).toBe(false);

    timeString = '16:60';
    valid = isValidTime(timeString);
    expect(valid).toBe(false);

    timeString = '17:88';
    valid = isValidTime(timeString);
    expect(valid).toBe(false);

    timeString = '30:30';
    valid = isValidTime(timeString);
    expect(valid).toBe(false);
  });

  it('phone validation', () => {
    let phone = '+381 63/1234-567';
    let valid = isValidPhoneNumber(phone);
    expect(valid).toBe(true);

    phone = '+381-63 1234/567';
    valid = isValidPhoneNumber(phone);
    expect(valid).toBe(true);

    phone = '+38562123456';
    valid = isValidPhoneNumber(phone);
    expect(valid).toBe(true);

    phone = '+387 60123456';
    valid = isValidPhoneNumber(phone);
    expect(valid).toBe(true);

    phone = '0631 234567';
    valid = isValidPhoneNumber(phone);
    expect(valid).toBe(false);

    phone = '+385 1234567890';
    valid = isValidPhoneNumber(phone);
    expect(valid).toBe(false);

    phone = '+387 1234a567';
    valid = isValidPhoneNumber(phone);
    expect(valid).toBe(false);
  });

  it('zoom id validation', () => {
    let zoomID = 123456789;
    let valid = isValidZoomID(zoomID);
    expect(valid).toBe(true);

    zoomID = 1234567890;
    valid = isValidZoomID(zoomID);
    expect(valid).toBe(true);

    zoomID = 12345678901;
    valid = isValidZoomID(zoomID);
    expect(valid).toBe(true);

    zoomID = 123456789012;
    valid = isValidZoomID(zoomID);
    expect(valid).toBe(false);

    zoomID = 12345678;
    valid = isValidZoomID(zoomID);
    expect(valid).toBe(false);
  });
});

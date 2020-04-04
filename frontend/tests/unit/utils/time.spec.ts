import { toDateTime } from '@/utils/time';
import { isValidDate, isValidTime } from '@/utils/validate';

describe('time utils', () => {
  it('conversion to datetime', () => {
    const dateTime = toDateTime('2020/04/05', '12:30');
    expect(dateTime.year).toBe(2020);
    expect(dateTime.month).toBe(4);
    expect(dateTime.day).toBe(5);

    expect(dateTime.hour).toBe(12);
    expect(dateTime.minute).toBe(30);
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
});

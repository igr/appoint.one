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
});

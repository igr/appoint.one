import { DateTime } from '@/model/DateTime';

export const toDateTime = (dateString: string, timeString: string): DateTime => ({
  year: Number(dateString.slice(0, 4)),
  month: Number(dateString.slice(5, 7)),
  day: Number(dateString.slice(8, 10)),
  hour: Number(timeString.slice(0, 2)),
  minute: Number(timeString.slice(3, 5)),
} as DateTime);

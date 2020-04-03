import { DateTime } from '@/model/DateTime';

export const toDateTime = (dateString: string, timeString: string): DateTime => ({
  year: Number(dateString.slice(0, 4)),
  month: Number(dateString.slice(5, 7)),
  day: Number(dateString.slice(8, 10)),
  hour: Number(timeString.slice(0, 2)),
  minute: Number(timeString.slice(3, 5)),
} as DateTime);

export const isInFuture = (datetime: DateTime): boolean => {
  const dateObj = new Date();
  const year = dateObj.getUTCFullYear();

  if (datetime.year > year) {
    return true;
  }
  const month = dateObj.getUTCMonth() + 1;
  if (datetime.month > month) {
    return true;
  }
  const day = dateObj.getUTCDate();
  if (datetime.day > day) {
    return true;
  }
  const hour = dateObj.getUTCHours();
  if (datetime.hour > hour) {
    return true;
  }
  const minutes = dateObj.getUTCMinutes();
  if (datetime.minute > minutes) {
    return true;
  }
  return false;
};

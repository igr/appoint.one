import { DateTime } from '@/model/DateTime';

export const toDateTime = (dateString: string, timeString: string): DateTime => {
  const dateParts = dateString.split('/');
  const timeParts = timeString.split(':');

  return {
    year: Number(dateParts[0]),
    month: Number(dateParts[1]),
    day: Number(dateParts[2]),
    hour: Number(timeParts[0]),
    minute: Number(timeParts[1]),
  } as DateTime;
};

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


const months = [
  'Januar', 'Februar', 'Mart', 'April', 'Maj', 'Juni', 'Juli', 'Avgust', 'Septembar', 'Oktobar', 'Novembar', 'Decembar',
];

export const toDateString = (datetime: DateTime): string => {
  let y = `-${datetime.year}`;
  if (datetime.year === new Date().getFullYear()) {
    y = '';
  }
  return `${datetime.day}-${months[datetime.month - 1]}${y}`;
};

export const toTimeString = (datetime: DateTime): string => {
  let h = `${datetime.hour}`;
  if (h.length === 1) {
    h = `0${h}`;
  }
  let m = `${datetime.minute}`;
  if (m.length === 1) {
    m = `0${m}`;
  }

  return `${h}:${m}`;
};

export const toDateTimeString = (datetime: DateTime): string => {
  const date = toDateString(datetime);
  const time = toTimeString(datetime);

  return `${date} ${time}`;
};

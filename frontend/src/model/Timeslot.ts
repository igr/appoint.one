import { DateTime } from '@/model/DateTime';
import { Doctor } from '@/model/Doctor';

export interface Timeslot {
  id: {
    value: number,
  },
  status: TimeslotStatus,
  datetime: DateTime,
  doctor: Doctor
}

export enum TimeslotStatus {
  NEW = 0,
  RESERVED = 10,
  CANCELED = 20,
  DONE = 30,
}

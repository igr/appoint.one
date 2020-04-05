import { DateTime } from '@/model/DateTime';
import { Doctor } from '@/model/Doctor';

export interface Timeslot {
  id: number,
  status: TimeslotStatus,
  datetime: DateTime,
  doctorId: number
}

export interface TimeslotAndDoctor {
  timeslot: Timeslot,
  doctor: Doctor
}

export enum TimeslotStatus {
  NEW = 0,
  RESERVED = 10,
  CANCELED = 20,
  DONE = 30,
}

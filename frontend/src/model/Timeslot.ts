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

export const isTimeslotNew = (timeslot: Timeslot) => timeslot.status === TimeslotStatus.NEW;
// eslint-disable-next-line max-len
export const isTimeslotReserved = (timeslot: Timeslot) => timeslot.status === TimeslotStatus.RESERVED;
// eslint-disable-next-line max-len
export const isTimeslotCanceled = (timeslot: Timeslot) => timeslot.status === TimeslotStatus.CANCELED;
export const isTimeslotDone = (timeslot: Timeslot) => timeslot.status === TimeslotStatus.DONE;

import { DateTime } from '@/model/DateTime';
import { Doctor } from '@/model/Doctor';

export interface Timeslot {
  id: number;
  status: TimeslotStatus;
  datetime: DateTime;
  doctorId: number;
}

export interface TimeslotAndDoctor {
  timeslot: Timeslot;
  doctor: Doctor;
}

export enum TimeslotStatus {
  NEW = 0,
  RESERVED = 10,
  CANCELED = 20,
  DONE = 30,
}

export const timeslotStatusName = (timeslotStatus: TimeslotStatus) => {
  switch (timeslotStatus) {
    case TimeslotStatus.NEW: return 'Novo';
    case TimeslotStatus.RESERVED: return 'Rezervisan';
    case TimeslotStatus.CANCELED: return 'Otkazan';
    case TimeslotStatus.DONE: return 'Završen';
    default: throw Error('Invalid status');
  }
};

export const isTimeslotNew = (timeslot: Timeslot) => timeslot.status
  === TimeslotStatus.NEW;
export const isTimeslotReserved = (timeslot: Timeslot) => timeslot.status
  === TimeslotStatus.RESERVED;
export const isTimeslotCanceled = (timeslot: Timeslot) => timeslot.status
  === TimeslotStatus.CANCELED;
export const isTimeslotDone = (timeslot: Timeslot) => timeslot.status
  === TimeslotStatus.DONE;

import { NewDoctor } from '@/model/NewDoctor';
import { User } from '@/model/User';
import { DateTime } from '@/model/DateTime';

export interface Timeslot {
  id: {
    value: number,
  },
  status: TimeslotStatus,
  datetime: DateTime,
  doctor: {
    id: {
      value: number,
    },
    data: NewDoctor,
    user: User,
  }
}

export enum TimeslotStatus {
  NEW = 0,
  RESERVED = 10,
  CANCELED = 20,
  DONE = 30,
}

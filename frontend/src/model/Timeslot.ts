import { NewDoctor } from '@/model/NewDoctor';
import { User } from '@/model/User';
import { DateTime } from '@/model/DateTime';

export interface Timeslot {
  datetime: DateTime,
  doctor: {
    id: {
      value: number,
    },
    data: NewDoctor,
    user: User,
  }
}

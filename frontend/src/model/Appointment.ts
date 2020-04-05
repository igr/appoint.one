import { Doctor } from '@/model/Doctor';
import { Timeslot } from '@/model/Timeslot';

export interface Appointment {
  timeslot: Timeslot,
  doctor: Doctor
}

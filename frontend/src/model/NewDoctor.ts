import { DoctorData } from '@/model/DoctorData';

export interface NewDoctor {
  password: string;
  regCode: string;
  doctor: DoctorData;
}

import { DoctorData } from '@/model/DoctorData';

export interface Doctor {
  id: number;
  data: DoctorData;
  userId: number;
}

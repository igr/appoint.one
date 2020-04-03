import { User } from '@/model/User';
import { DoctorData } from '@/model/DoctorData';

export interface Doctor {
  id: number,
  data: DoctorData,
  user: User,
}

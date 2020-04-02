import { DoctorData } from '@/model/DoctorData';

export class NewDoctor implements DoctorData {
  name: string = '';

  email: string = '';

  sex: boolean = true;

  country: number = 1;

  year: number = 1980;

  occupation: string = '';

  education: number = 1;

  phone: string = '';

  zoom: string = '';

  pic = false;
}

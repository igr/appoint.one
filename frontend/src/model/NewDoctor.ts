import { DoctorData } from '@/model/DoctorData';

export class NewDoctor implements DoctorData {
  name: string = '';

  email: string = '';

  sex: boolean = true;

  country: number = 1;

  year: number = 1980;

  occupation: number = 1;

  occupation2: string = '';

  occupationSpec: string = '';

  certificate: number = 1;

  modalitet: number = 1;

  modalitet2: string = '';

  education: number = 1;

  phone: string = '';

  zoom: string = '';

  pic = false;
}

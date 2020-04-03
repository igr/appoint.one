import http from '@/utils/http';
import { NewDoctor } from '@/model/NewDoctor';

class DoctorApi {
  getDoctor = (docId: number) => http({
    url: `doctors/${docId}`,
    method: 'get',
  });

  postNewDoctor = (doctor: NewDoctor) => http({
    url: 'doctors',
    method: 'post',
    data: {
      doctor: {
        ...doctor,
        sex: doctor.sex ? 'MALE' : 'FEMALE',
      },
      user: {
        name: doctor.email,
        password: Math.random().toString(36).slice(-8),
        role: 'DOC',
      },
    },
  });
}

export default new DoctorApi();

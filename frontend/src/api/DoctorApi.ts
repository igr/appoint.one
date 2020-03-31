import http from '@/utils/http';
import { NewDoctor } from '@/model/NewDoctor';

class DoctorApi {
  get() {
    return http.get('/doctors');
  }

  postNewDoctor = (doctor: NewDoctor) => http({
    url: 'doctors',
    method: 'post',
    data: {
      ...doctor,
      sex: doctor.sex ? 'MALE' : 'FEMALE',
    },
  });
}

export default new DoctorApi();

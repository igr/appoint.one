import http from '@/utils/http';
import { NewDoctor } from '@/model/NewDoctor';

class DoctorApi {
  getAll = () => http({
    url: 'doctors',
    method: 'get',
  });

  getDoctor = (docId: number) => http({
    url: `doctors/${docId}`,
    method: 'get',
  });

  postNewDoctor = (newDoctor: NewDoctor) => http({
    url: 'doctors',
    method: 'post',
    data: {
      ...newDoctor,
      name: newDoctor.doctor.email,
      doctor: {
        ...newDoctor.doctor,
        sex: newDoctor.doctor.sex ? 'MALE' : 'FEMALE',
      },
    },
  });

  getDoctorTimeslots = (docId: number) => http({
    url: `doctors/${docId}/timeslots`,
    method: 'get',
  });
}

export default new DoctorApi();

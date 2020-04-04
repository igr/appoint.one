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

  confirmDoctor = (docId: number) => http({
    url: `doctors/${docId}/enable`,
    method: 'put',
  });

  deConfirmDoctor = (docId: number) => http({
    url: `doctors/${docId}/disable`,
    method: 'put',
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

  getDoctorTimeslots = (docId: number) => http({
    url: `doctors/${docId}/timeslots`,
    method: 'get',
  });
}

export default new DoctorApi();

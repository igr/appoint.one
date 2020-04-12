import http from '@/utils/http';
import { DateTime } from '@/model/DateTime';

class TimeslotApi {
  count = () => http({
    url: '/timeslots/count',
    method: 'get',
  });

  listNextTimeslots = () => http({
    url: '/timeslots/available',
    method: 'get',
  });

  reserveTimeslot = (timeslotId: number) => http({
    url: `/timeslots/${timeslotId}/reserve`,
    method: 'put',
  });

  cancelTimeslot = (timeslotId: number) => http({
    url: `/timeslots/${timeslotId}/cancel`,
    method: 'put',
  });

  activateTimeslot = (timeslotId: number) => http({
    url: `/timeslots/${timeslotId}/activate`,
    method: 'put',
  });

  get = (timeslotId: number) => http({
    url: `/timeslots/${timeslotId}`,
    method: 'get',
  });

  post = (doctorId: number, datetime: DateTime) => http({
    url: '/timeslots/',
    method: 'post',
    data: {
      doctorId,
      datetime,
    },
  });

  delete = (timeslotId: number) => http({
    url: `/timeslots/${timeslotId}`,
    method: 'delete',
  });
}

export default new TimeslotApi();

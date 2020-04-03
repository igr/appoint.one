import http from '@/utils/http';
import { DateTime } from '@/model/DateTime';

class TimeslotApi {
  count = () => http({
    url: '/timeslots/count',
    method: 'get',
  });

  listNextTimeslots = (country: string, city: string) => http({
    url: '/timeslots/available',
    method: 'get',
    params: {
      country,
      city,
    },
  });

  reserveTimeslot = (timeslotId: number) => http({
    url: `/timeslots/${timeslotId}/reserve`,
    method: 'put',
  });

  get = (timeslotId: number) => http({
    url: `/timeslots/${timeslotId}`,
    method: 'get',
  });

  post = (doctorId: Number, datetime: DateTime) => http({
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
  })
}

export default new TimeslotApi();

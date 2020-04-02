import http from '@/utils/http';

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
  })
}

export default new TimeslotApi();

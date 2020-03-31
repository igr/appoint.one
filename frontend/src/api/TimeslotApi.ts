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
}

export default new TimeslotApi();

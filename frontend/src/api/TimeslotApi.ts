import http from '@/utils/http';

class TimeslotApi {
  count = () => http({
    url: '/timeslots/count',
    method: 'get',
  });
}

export default new TimeslotApi();

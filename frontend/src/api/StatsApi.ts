import http from '@/utils/http';

class StatsApi {
  get = () => http({
    url: 'stats',
    method: 'get',
  });

  getDoctors = () => http({
    url: 'stats/doctors',
    method: 'get',
  });
}

export default new StatsApi();

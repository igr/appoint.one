import http from '@/utils/http';

class StatsApi {
  get = () => http({
    url: 'stats',
    method: 'get',
  });
}

export default new StatsApi();

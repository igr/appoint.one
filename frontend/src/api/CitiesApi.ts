import http from '@/utils/http';

class CitiesApi {
  cities = () => http({
    url: '/data/cities',
    method: 'get',
  });
}

export default new CitiesApi();

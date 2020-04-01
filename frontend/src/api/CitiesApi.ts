import http from '@/utils/http';

class CitiesApi {
  cities = () => http({
    url: '/data/cities',
    method: 'get',
  });

  countries = () => http({
    url: '/data/countries',
    method: 'get',
  });
}

export default new CitiesApi();

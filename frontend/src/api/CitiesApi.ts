import http from '@/utils/http';

class CitiesApi {
  get() {
    return http.get('/data/cities');
  }
}

export default new CitiesApi();

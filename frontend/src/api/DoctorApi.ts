import http from '@/utils/http';

class DoctorApi {
  get() {
    return http.get('/doctors');
  }

  post(name: string) {
    return http.post('/doctors', {
      name,
    });
  }
}

export default new DoctorApi();

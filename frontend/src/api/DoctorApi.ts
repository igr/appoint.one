import { AxiosInstance } from 'axios';

class DoctorApi {
  private readonly http: AxiosInstance;

  constructor(http: AxiosInstance) {
    this.http = http;
  }

  get() {
    return this.http.get('/doctors');
  }

  post(name: string) {
    return this.http.post('/doctors', {
      name,
    });
  }
}

export default DoctorApi;

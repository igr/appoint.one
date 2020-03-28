import { AxiosInstance } from 'axios';

class DoctorApi {
  private readonly http: AxiosInstance;

  private readonly url: string = '/doctor';

  constructor(http: AxiosInstance) {
    this.http = http;
  }

  get() {
    return this.http.get(this.url);
  }

  post(name: string) {
    return this.http.post(this.url, {
      name,
    });
  }
}

export default DoctorApi;

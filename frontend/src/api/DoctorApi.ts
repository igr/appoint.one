import http from '@/utils/http';

class DoctorApi {
  private readonly http = http;

  private readonly url: string = '/doctors';

  get() {
    return this.http.get(this.url);
  }

  post(name: string) {
    return this.http.post(this.url, {
      name,
    });
  }
}

export default new DoctorApi();

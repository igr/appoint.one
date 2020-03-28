import http from '@/utils/http';

class TimeslotApi {
  private readonly http = http;

  private readonly url: string = '/timeslots';

  get() {
    return this.http.get(this.url);
  }

  count() {
    return this.http.get(`${this.url}/count`);
  }
}

export default new TimeslotApi();

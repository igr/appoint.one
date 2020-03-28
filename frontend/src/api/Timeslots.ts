import { AxiosInstance } from 'axios';

class TimeslotsApi {
  private readonly http: AxiosInstance;

  private readonly url: string = 'timeslot';

  constructor(http: AxiosInstance) {
    this.http = http;
  }

  get() {
    return this.http.get(this.url);
  }

  count() {
    return this.http.get(`${this.url}/count`);
  }
}

export default TimeslotsApi;

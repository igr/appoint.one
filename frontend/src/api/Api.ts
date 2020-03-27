import axios, { AxiosInstance } from 'axios';
import DoctorApi from './DoctorApi';

class Api {
  private readonly _http: AxiosInstance;

  private readonly _doctors: DoctorApi;

  constructor(http: AxiosInstance) {
    this._http = http;
    this._doctors = new DoctorApi(this._http);
  }

  get doctors(): DoctorApi {
    return this._doctors;
  }
}

export default new Api(axios.create({
  baseURL: process.env.API_ENDPOINT,
  headers: {
    'Content-Type': 'application/json',
  },
}));

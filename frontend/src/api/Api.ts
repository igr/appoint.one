import http from '@/utils/http';
import DoctorApi from './DoctorApi';

class Api {
  private readonly _doctors: DoctorApi;

  constructor() {
    this._doctors = new DoctorApi(http);
  }

  get doctors(): DoctorApi {
    return this._doctors;
  }
}

export default new Api();

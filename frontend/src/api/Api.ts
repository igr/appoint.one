import http from '@/utils/http';
import TimeslotsApi from '@/api/Timeslots';
import DoctorApi from '@/api/DoctorApi';

class Api {
  private readonly _doctor: DoctorApi;

  private readonly _timeslot: TimeslotsApi;

  constructor() {
    this._doctor = new DoctorApi(http);
    this._timeslot = new TimeslotsApi(http);
  }

  get doctor(): DoctorApi {
    return this._doctor;
  }

  get timeslot(): TimeslotsApi {
    return this._timeslot;
  }
}

export default new Api();

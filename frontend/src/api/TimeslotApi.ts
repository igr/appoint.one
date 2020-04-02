import http, { http2 } from '@/utils/http';
import { AppModule } from '@/store/modules/app';

class TimeslotApi {
  count = () => http({
    url: '/timeslots/count',
    method: 'get',
  });

  listNextTimeslots = (country: string, city: string) => http({
    url: '/timeslots/available',
    method: 'get',
    params: {
      country,
      city,
    },
  });

  reserveTimeslot = (timeslotId: number) => http2({
    url: `/timeslots/${timeslotId}/reserve`,
    method: 'put',
  }, {
    409: () => {
      AppModule.setInfoMessage('Rezervisanje nije uspelo. Pokušajte ponovo.');
      return true;
    },
  })
}

export default new TimeslotApi();

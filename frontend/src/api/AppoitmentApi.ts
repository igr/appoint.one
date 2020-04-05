import http from '@/utils/http';

class AppoitmentApi {
  get = (timeslotId: number) => http({
    url: `/appointments/${timeslotId}`,
    method: 'get',
  });
}

export default new AppoitmentApi();

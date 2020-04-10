import http from '@/utils/http';

class DocApi {
  get = (id: number) => http({
    url: `/docs/${id}`,
    method: 'get',
  });
}

export default new DocApi();

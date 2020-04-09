import { httpLocal } from '@/utils/http';

class DocApi {
  get = (name: string) => httpLocal({
    url: `/docs/${name}`,
    method: 'get',
  });
}

export default new DocApi();

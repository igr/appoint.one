import http from '@/utils/http';

class EmailApi {
  test = () => http({
    url: 'mails',
    method: 'post',
  });
}

export default new EmailApi();

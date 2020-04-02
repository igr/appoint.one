import http from '@/utils/http';

class UserApi {
  login = (data: { name: string, password: string }) => http({
    url: '/users/login',
    method: 'post',
    data,
  });

  async logout() {
    return '';
  }
}

export default new UserApi();

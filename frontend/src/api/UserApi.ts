import http from '@/utils/http';

class UserApi {
  login = (data: { name: string, password: string }) => http({
    url: '/users/login',
    method: 'post',
    data,
    errorHandle: true,
  });

  async getUserInfo(param: {}) {
    return {
      data: {
        user: {
          roles: ['doctor'], name: 'Pera', avatar: '', introduction: '', email: '',
        },
      },
    };
  }

  async logout() {
    return '';
  }
}

export default new UserApi();

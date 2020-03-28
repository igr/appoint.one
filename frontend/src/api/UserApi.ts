import http from '@/utils/http';

class UserApi {
  private readonly http = http;

  private readonly url: string = '/user';

  async login(param: { password: string; username: string }) {
    return { data: { accessToken: '123' } };
  }

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

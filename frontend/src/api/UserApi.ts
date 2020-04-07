import http from '@/utils/http';

class UserApi {
  login = (data: { name: string; password: string }) => http({
    url: '/users/login',
    method: 'post',
    data,
  });

  getUser = (userId: number) => http({
    url: `users/${userId}`,
    method: 'get',
  });

  updatePassword = (userId: number, newPassword: string) => http({
    url: `users/${userId}/password`,
    method: 'put',
    data: {
      password: newPassword,
    },
  });

  async logout() {
    return '';
  }
}

export default new UserApi();

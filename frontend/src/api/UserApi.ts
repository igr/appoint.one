import http from '@/utils/http';
import { User } from '@/model/User';

class UserApi {
  login = (data: { name: string, password: string }) => http({
    url: '/users/login',
    method: 'post',
    data,
  });

  getUser = (userId: number) => http({
    url: `users/${userId}`,
    method: 'get',
  });

  modifyUserData = (userId: number, newPassword: string) => http({
    url: 'admin/modifyUserData',
    method: 'post',
    data: {
      id: userId,
      pass: newPassword,
    },
  });

  async logout() {
    return '';
  }
}

export default new UserApi();

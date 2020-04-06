import http from '@/utils/http';

class UserApi {
  login = (data: { name: string, password: string }) => http({
    url: '/users/login',
    method: 'post',
    data,
  });

  getUser = (userId: number) => http({
    url: `user/${userId}`,
    method: 'get',
  });

  modifyUserData = (data: {userId: number, password: string}) => http({
    url: 'admin/modifyUserData',
    method: 'post',
    data,
  });

  async logout() {
    return '';
  }
}

export default new UserApi();

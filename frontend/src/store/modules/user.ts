import {
  VuexModule, Module, Action, Mutation, getModule,
} from 'vuex-module-decorators';
import UserApi from '@/api/UserApi';
import AppCookies from '@/utils/cookies';
import { resetRouter } from '@/router';
import store from '@/store';

export interface UserState {
  id: number,
  token: string
  name: string
  roles: string[]
}

@Module({ dynamic: true, store, name: 'user' })
class UserModuleClass extends VuexModule implements UserState {
  public token = AppCookies.getToken() || '';

  public id = 0;

  public name = '';

  public roles: string[] = [];

  @Mutation
  private SET_TOKEN(token: string) {
    this.token = token;
  }

  @Mutation
  private SET_NAME(name: string) {
    this.name = name;
  }

  @Mutation
  private SET_ID(id: number) {
    this.id = id;
  }

  @Mutation
  private SET_ROLES(roles: string[]) {
    this.roles = roles;
  }

  @Action
  public async Login(userInfo: { name: string, password: string}): Promise<number> {
    let { name } = userInfo;
    name = name.trim();
    const { password } = userInfo;
    try {
      const res = await UserApi.login({ name, password });
      const { data } = res;

      if (!data.token || data.token === '') {
        return 404;
      }

      AppCookies.setToken(data.token);
      this.SET_TOKEN(data.token);
      this.SET_ROLES([data.role]);
      this.SET_NAME(data.name);
      this.SET_ID(data.id);
      return 200;
    } catch (error) {
      return error.response.status;
    }
  }

  @Action
  public ResetToken() {
    AppCookies.removeToken();
    this.SET_TOKEN('');
    this.SET_ID(0);
    this.SET_NAME('');
    this.SET_ROLES([]);
  }

  @Action
  public async LogOut() {
    await UserApi.logout();
    this.ResetToken();
    resetRouter();
  }

  /**
   * Returns true if user is logged in.
   */
  get isUserLoggedIn() {
    return this.token !== '';
  }

  get hasAccess(): (permissionRoles: string[]) => boolean {
    return (permissionRoles: string[]) => this.roles.some((role) => permissionRoles.includes(role));
  }
}

export const UserModule = getModule(UserModuleClass);

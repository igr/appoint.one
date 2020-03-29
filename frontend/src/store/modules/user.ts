import {
  VuexModule, Module, Action, Mutation, getModule,
} from 'vuex-module-decorators';
import UserApi from '@/api/UserApi';
import AppCookies from '@/utils/cookies';
import { resetRouter } from '@/router';
import store from '@/store';

export interface UserState {
  token: string
  name: string
  avatar: string
  introduction: string
  roles: string[]
  email: string
}

@Module({ dynamic: true, store, name: 'user' })
class User extends VuexModule implements UserState {
  public token = AppCookies.getToken() || '';

  public name = '';

  public avatar = '';

  public introduction = '';

  public roles: string[] = [];

  public email = '';

  @Mutation
  private SET_TOKEN(token: string) {
    this.token = token;
  }

  @Mutation
  private SET_NAME(name: string) {
    this.name = name;
  }

  @Mutation
  private SET_AVATAR(avatar: string) {
    this.avatar = avatar;
  }

  @Mutation
  private SET_INTRODUCTION(introduction: string) {
    this.introduction = introduction;
  }

  @Mutation
  private SET_ROLES(roles: string[]) {
    this.roles = roles;
  }

  @Mutation
  private SET_EMAIL(email: string) {
    this.email = email;
  }

  @Action
  public async Login(userInfo: { email: string, password: string}): Promise<boolean> {
    let { email } = userInfo;
    email = email.trim();
    const { password } = userInfo;
    const res = await UserApi.login({ email, password }).catch((_) => ({ data: { token: '' } }));
    const data = res && res.data;

    if (data.token === '') {
      return false;
    }

    AppCookies.setToken(data.token);
    this.SET_TOKEN(data.token);
    this.GetUserInfo();
    return true;
  }

  @Action
  public ResetToken() {
    AppCookies.removeToken();
    this.SET_TOKEN('');
    this.SET_ROLES([]);
  }

  @Action
  public async GetUserInfo() {
    if (this.token === '') {
      throw Error('GetUserInfo: token is undefined!');
    }
    const { data } = await UserApi.getUserInfo({ });
    if (!data) {
      throw Error('Verification failed, please Login again.');
    }
    const {
      roles, name, avatar, introduction, email,
    } = data.user;
    // roles must be a non-empty array
    if (!roles || roles.length <= 0) {
      throw Error('GetUserInfo: roles must be a non-null array!');
    }
    this.SET_ROLES(roles);
    this.SET_NAME(name);
    this.SET_AVATAR(avatar);
    this.SET_INTRODUCTION(introduction);
    this.SET_EMAIL(email);
  }

  @Action
  public async LogOut() {
    await UserApi.logout();
    AppCookies.removeToken();
    resetRouter();

    this.SET_TOKEN('');
    this.SET_ROLES([]);
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

export const UserModule = getModule(User);

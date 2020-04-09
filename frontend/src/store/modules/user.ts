import {
  VuexModule, Module, Action, Mutation,
} from 'vuex-module-decorators';
import { Vue } from 'vue-property-decorator';
import UserApi from '@/api/UserApi';
// import AppCookies from '@/utils/cookies';
import { resetRouter } from '@/router';
import { Doctor } from '@/model/Doctor';
import DoctorApi from '@/api/DoctorApi';

export interface UserState {
  id: number;
  token: string;
  name: string;
  roles: string[];
  doctor: Doctor;
}

@Module({ name: 'user' })
export class UserModuleClass extends VuexModule implements UserState {
  // public token = AppCookies.getToken() || '';
  public token = '';

  public id = 0;

  public name = '';

  public roles: string[] = [];

  public doctor: Doctor = {} as Doctor;

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

  @Mutation
  private SET_DOCTOR(data: any) {
    // Must make it REACTIVE!
    Object.keys(data).forEach((key) => {
      Vue.set(this.doctor, key, data[key]);
    });
  }

  @Action
  public async Login(userInfo: { name: string; password: string }): Promise<number> {
    let { name } = userInfo;
    name = name.trim();
    const { password } = userInfo;
    try {
      const res = await UserApi.login({ name, password });
      const { data } = res;

      if (!data.token || data.token === '') {
        return 404;
      }

      // AppCookies.setToken(data.token);
      this.SET_TOKEN(data.token);
      this.SET_ROLES([data.role]);
      this.SET_NAME(data.name);
      this.SET_ID(data.id);

      if (this.isDoctor) {
        const doc = await DoctorApi.getDoctor(data.id);
        this.SET_DOCTOR(doc.data);
      }
      return 200;
    } catch (error) {
      return error.response.status;
    }
  }

  @Action
  public ResetToken() {
    // AppCookies.removeToken();
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

  get isDoctor() {
    return this.hasAccess(['DOC']);
  }

  get isAdmin() {
    return this.hasAccess(['ADMIN']);
  }
}

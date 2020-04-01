import {
  VuexModule, Module, Action, Mutation, getModule,
} from 'vuex-module-decorators';
import store from '@/store';
import DoctorApi from '@/api/DoctorApi';
import { UserModule } from '@/store/modules/user';

export interface DoctorState {
  name: string,
}

@Module({ dynamic: true, store, name: 'user' })
class DoctorClass extends VuexModule implements DoctorState {
  public name = '';

  @Mutation
  private SET_NAME(name: string) {
    this.name = name;
  }

  @Action
  public async GetDoctorInfo() {
    const { data } = await DoctorApi.getDoctor(UserModule.id);

    this.SET_NAME(data.name);
  }
}

export const DoctorModule = getModule(DoctorClass);

import {
  VuexModule, Module, Action, Mutation,
} from 'vuex-module-decorators';
import DoctorApi from '@/api/DoctorApi';
import { UserModule } from '@/store';

export interface DoctorState {
  name: string;
}

@Module({ name: 'doctor' })
export class DoctorModuleClass extends VuexModule implements DoctorState {
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

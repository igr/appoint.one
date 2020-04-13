import {
  VuexModule, Module, Mutation, Action,
} from 'vuex-module-decorators';

export interface AppState {
  alert: {
    message: string;
    type: string;
  };
}

@Module({ name: 'app' })
export class AppModuleClass extends VuexModule implements AppState {
  public alert = {
    message: '',
    type: 'error',
  };

  @Mutation
  private SET_ALERT(arg: { message: string; type: string }) {
    this.alert.message = arg.message;
    this.alert.type = arg.type;
  }

  @Action
  public setAlert(arg: { message: string; type: string }) {
    this.SET_ALERT(arg);
  }

  @Action
  public clearAlert() {
    this.SET_ALERT({ message: '', type: 'error' });
  }
}

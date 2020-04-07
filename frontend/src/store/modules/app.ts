import {
  VuexModule, Module, Mutation, Action, getModule,
} from 'vuex-module-decorators';
import store from '@/store';

export interface AppState {
  alertMessage: string;
  info: {
    message: string;
    type: string;
  };
}

@Module({ dynamic: true, store, name: 'app' })
class AppModuleClass extends VuexModule implements AppState {
  public alertMessage = '';

  public info = {
    message: '',
    type: 'error',
  };

  @Mutation
  private SET_ALERT_MESSAGE(message: string) {
    this.alertMessage = message;
  }

  @Action
  public setAlertMessage(message: string) {
    this.SET_ALERT_MESSAGE(message);
  }

  @Mutation
  private SET_INFO(arg: { message: string; type: string }) {
    this.info.message = arg.message;
    this.info.type = arg.type;
  }

  @Action
  public setInfo(arg: { message: string; type: string }) {
    this.SET_INFO(arg);
    setTimeout(() => this.clearInfo(), 3000);
  }

  @Action
  public clearAlertMessage() {
    this.SET_ALERT_MESSAGE('');
  }

  @Action
  public clearInfo() {
    this.SET_INFO({ message: '', type: '' });
  }
}

export const AppModule = getModule(AppModuleClass);

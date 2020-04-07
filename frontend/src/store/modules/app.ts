import {
  VuexModule, Module, Mutation, Action, getModule,
} from 'vuex-module-decorators';
import store from '@/store';

export interface AppState {
  alertMessage: string;
  infoMessage: string;
}

@Module({ dynamic: true, store, name: 'app' })
class AppModuleClass extends VuexModule implements AppState {
  public alertMessage = '';

  public infoMessage = '';

  @Mutation
  private SET_ALERT_MESSAGE(message: string) {
    this.alertMessage = message;
  }

  @Action
  public setAlertMessage(message: string) {
    this.SET_ALERT_MESSAGE(message);
  }

  @Mutation
  private SET_INFO_MESSAGE(message: string) {
    this.infoMessage = message;
  }

  @Action
  public setInfoMessage(message: string) {
    this.SET_INFO_MESSAGE(message);
    setTimeout(() => this.SET_INFO_MESSAGE(''), 3000);
  }

  @Action
  public clearAlertMessage() {
    this.SET_ALERT_MESSAGE('');
  }

  @Action
  public clearInfoMessage() {
    this.SET_INFO_MESSAGE('');
  }
}

export const AppModule = getModule(AppModuleClass);

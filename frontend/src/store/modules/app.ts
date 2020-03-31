import {
  VuexModule, Module, Mutation, Action, getModule,
} from 'vuex-module-decorators';
import store from '@/store';

export interface AppState {
  alertMessage: string;
}

@Module({ dynamic: true, store, name: 'app' })
class App extends VuexModule implements AppState {
  public alertMessage: string = '';

  @Mutation
  private SET_ALERT_MESSAGE(message: string) {
    this.alertMessage = message;
  }

  @Action
  public setAlertMessage(message: string) {
    this.SET_ALERT_MESSAGE(message);
  }

  @Action
  public clearAlertMessage() {
    this.SET_ALERT_MESSAGE('');
  }
}

export const AppModule = getModule(App);

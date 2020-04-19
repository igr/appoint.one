import {
  VuexModule, Module, Mutation, Action,
} from 'vuex-module-decorators';

export interface AppState {
  alert: {
    message: string;
    type: string;
  };
  // Add to Home Screen
  a2hs: {
    triggered: boolean;
    accepted: boolean;
  };
}

@Module({ name: 'app' })
export class AppModuleClass extends VuexModule implements AppState {
  public alert = {
    message: '',
    type: 'error',
  };

  public a2hs = {
    triggered: true,
    accepted: true,
  };

  @Mutation
  private SET_ALERT(arg: AppState['alert']) {
    this.alert.message = arg.message;
    this.alert.type = arg.type;
  }

  @Action
  public setAlert(arg: AppState['alert']) {
    this.SET_ALERT(arg);
  }

  @Action
  public clearAlert() {
    this.SET_ALERT({ message: '', type: 'error' });
  }

  @Mutation
  private SET_A2HS(arg: AppState['a2hs']) {
    this.a2hs.triggered = arg.triggered;
    this.a2hs.accepted = arg.accepted;
  }

  @Action
  public setA2HS(arg: AppState['a2hs']) {
    this.SET_A2HS(arg);
  }

  get hasA2HSTriggered() {
    return this.a2hs.triggered;
  }
}

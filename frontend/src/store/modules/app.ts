import {
  VuexModule, Module, Mutation, Action, getModule,
} from 'vuex-module-decorators';
import AppCookies from '@/utils/cookies';
import store from '@/store';

export enum DeviceType {
  Mobile,
  Desktop,
}

export interface AppState {
  device: DeviceType;
  size: string;
}

@Module({ dynamic: true, store, name: 'app' })
class App extends VuexModule implements AppState {
  public device = DeviceType.Desktop;

  public size = AppCookies.getSize() || 'medium';

  @Mutation
  private TOGGLE_DEVICE(device: DeviceType) {
    this.device = device;
  }

  @Mutation
  private SET_SIZE(size: string) {
    this.size = size;
    AppCookies.setSize(this.size);
  }

  @Action
  public ToggleDevice(device: DeviceType) {
    this.TOGGLE_DEVICE(device);
  }

  @Action
  public SetSize(size: string) {
    this.SET_SIZE(size);
  }
}

export const AppModule = getModule(App);

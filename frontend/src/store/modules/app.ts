import {
  VuexModule, Module, Mutation, Action, getModule,
} from 'vuex-module-decorators';
import store from '@/store';

export interface AppState {
  error: string;
}

@Module({ dynamic: true, store, name: 'app' })
class App extends VuexModule implements AppState {
  public error: string = '';

  @Mutation
  private SET_ERROR(message: string) {
    this.error = message;
  }

  @Action
  public setError(message: string) {
    this.SET_ERROR(message);
  }
}

export const AppModule = getModule(App);

import {
  VuexModule, Module, Mutation, Action, getModule,
} from 'vuex-module-decorators';
import store from '@/store';

interface ErrorLogData {
  err: Error;
  info: string;
  url: string;
}

export interface ErrorLogState {
  logs: ErrorLogData[];
}

@Module({ dynamic: true, store, name: 'errorLog' })
class ErrorLog extends VuexModule implements ErrorLogState {
  public logs: ErrorLogData[] = [];

  @Mutation
  private ADD_ERROR_LOG(log: ErrorLogData) {
    this.logs.push(log);
  }

  @Mutation
  private CLEAR_ERROR_LOG() {
    this.logs.splice(0);
  }

  @Action
  public AddErrorLog(log: ErrorLogData) {
    this.ADD_ERROR_LOG(log);
  }

  @Action
  public ClearErrorLog() {
    this.CLEAR_ERROR_LOG();
  }
}

export const ErrorLogModule = getModule(ErrorLog);

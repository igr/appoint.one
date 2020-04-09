import {
  VuexModule, Module, Mutation, Action,
} from 'vuex-module-decorators';

interface ErrorLogData {
  err: Error;
  info: string;
  url: string;
}

export interface ErrorLogState {
  logs: ErrorLogData[];
}

@Module({ name: 'errorLog' })
export class ErrorLogClass extends VuexModule implements ErrorLogState {
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

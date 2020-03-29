import Vue from 'vue';
import Vuex from 'vuex';
import { ErrorLogState } from './modules/error-log';
import { UserState } from './modules/user';

Vue.use(Vuex);

export interface RootState {
  errorLog: ErrorLogState;
  user: UserState;
}

// Declare empty store first, dynamically register all modules later
export default new Vuex.Store<RootState>({});

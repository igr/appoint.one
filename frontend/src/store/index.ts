import Vue from 'vue';
import Vuex from 'vuex';
import { UserState } from '@/store/modules/user';
import { DoctorState } from '@/store/modules/doctor';

Vue.use(Vuex);

export interface RootState {
  // errorLog: ErrorLogState;
  user: UserState;
  doctorState: DoctorState;
}

// Declare empty store first, dynamically register all modules later
export default new Vuex.Store<RootState>({});

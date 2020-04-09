import Vue from 'vue';
import Vuex from 'vuex';
import VuexPersistence from 'vuex-persist';
import { AppState, AppModuleClass } from '@/store/modules/app';
import { UserState, UserModuleClass } from '@/store/modules/user';
import { ErrorLogState, ErrorLogClass } from '@/store/modules/error-log';
import { DoctorState, DoctorModuleClass } from '@/store/modules/doctor';
import { getModule } from 'vuex-module-decorators';

Vue.use(Vuex);

const vuexLocal = new VuexPersistence<RootState>({
  storage: window.localStorage,
});

export interface RootState {
  // errorLog: ErrorLogState;
  appState: AppState;
  user: UserState;
  doctorState: DoctorState;
  errorLogState: ErrorLogState;
}

const store = new Vuex.Store({
  modules: {
    app: AppModuleClass,
    user: UserModuleClass,
    doctor: DoctorModuleClass,
    errorLog: ErrorLogClass,
  },
  plugins: [vuexLocal.plugin],
});

export default store;
export const AppModule = getModule(AppModuleClass, store);
export const UserModule = getModule(UserModuleClass, store);
export const DoctorModule = getModule(DoctorModuleClass, store);
export const ErrorLogModule = getModule(ErrorLogClass, store);

import Vue from 'vue';
import { ErrorLogModule } from '@/store/modules/error-log';

const errorLogEnabled = true;

if (errorLogEnabled) {
  Vue.config.errorHandler = (err, vm, info) => {
    ErrorLogModule.AddErrorLog({
      err,
      info,
      url: window.location.href,
    });
  };
}

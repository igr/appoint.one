import Vue, { DirectiveOptions } from 'vue';
import ElementUI from 'element-ui';

import '@/styles/element-variables.scss';
import '@/styles/index.scss';

import App from '@/App.vue';
import store from '@/store';
import { AppModule } from '@/store/modules/app';
import router from '@/router';
import i18n from '@/lang';

import * as directives from '@/directives';
import * as filters from '@/filters';

Vue.config.productionTip = false;

Vue.use(ElementUI, {
  size: AppModule.size,
  i18n: (key: string, value: string) => i18n.t(key, value),
});

// Register global directives
Object.keys(directives).forEach((key) => {
  Vue.directive(key, (directives as { [key: string ]: DirectiveOptions })[key]);
});

// Register global filter functions
Object.keys(filters).forEach((key) => {
  Vue.filter(key, (filters as { [key: string ]: Function })[key]);
});

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  i18n,
  render: (h) => h(App),
}).$mount('#app');

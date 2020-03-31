import Vue, { DirectiveOptions } from 'vue';

// import '@/styles/index.scss';

import App from '@/App.vue';
import store from '@/store';
import router from '@/router';
import i18n from '@/lang';

import * as directives from '@/directives';
import * as filters from '@/filters';
import vuetify from './plugins/vuetify';

Vue.config.productionTip = false;

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
  vuetify,
  render: (h) => h(App),
}).$mount('#app');

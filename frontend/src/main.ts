import Vue, { DirectiveOptions } from 'vue';
import ElementUI from 'element-ui';

import '@/styles/element-variables.scss';
import '@/styles/index.scss';

import App from '@/App.vue';
import store from '@/store';
import { AppModule } from '@/store/modules/app';
import router from '@/router';

// import * as directives from '@/directives';
import * as filters from '@/filters';

Vue.config.productionTip = false;


Vue.use(ElementUI, {
  size: AppModule.size,
});

// Register global directives
// Object.keys(directives).forEach((key) => {
//   Vue.directive(key, (directives as { [key: string ]: DirectiveOptions })[key]);
// });

// Register global filter functions
Object.keys(filters).forEach((key) => {
  Vue.filter(key, (filters as { [key: string ]: Function })[key]);
});

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');

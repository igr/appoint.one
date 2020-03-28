import Vue from 'vue';
import Router, { RouteConfig } from 'vue-router';

/* Layout */
import Layout from '@/layout/index.vue';

Vue.use(Router);

export const constantRoutes: RouteConfig[] = [
  {
    path: '/404',
    component: () => import('@/views/error-page/404.vue'),
    meta: { hidden: true },
  },
  {
    path: '/',
    component: Layout,
  },
  { path: '*', redirect: '/404' },
];


const createRouter = () => new Router({
  routes: constantRoutes,
});

const router = createRouter();

export function resetRouter() {
  const newRouter = createRouter();
  (router as any).matcher = (newRouter as any).matcher;
}

export default router;

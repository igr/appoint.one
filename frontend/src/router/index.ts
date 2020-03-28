import Vue from 'vue';
import Router, { RouteConfig } from 'vue-router';

/* Layout */
import Layout from '@/layout/index.vue';

Vue.use(Router);

export const constantRoutes: RouteConfig[] = [
  {
    path: '/404',
    component: () => import('@/views/error-page/404.vue'),
  },
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        component: () => import('@/views/home/index.vue'),
        name: 'Home',
      },
    ],
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

import Vue from 'vue';
import Router, { Route, RouteConfig } from 'vue-router';

/* Layout */
import Layout from '@/layout/index.vue';
import { UserModule } from '@/store/modules/user';

Vue.use(Router);

export const routes: RouteConfig[] = [
  {
    path: '/redirect',
    component: Layout,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index.vue'),
      },
    ],
  },
  {
    path: '/login',
    component: () => import('@/views/login/index.vue'),
  },
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
      {
        path: 'doctor',
        component: () => import('@/views/doctor/index.vue'),
        name: 'Doctor',
        meta: {
          permission: {
            role: ['doctor'],
            access: true,
          },
        },
      },
    ],
  },
  { path: '*', redirect: '/404' },
];

const createRouter = () => new Router({
  routes,
});

const router = createRouter();

function _hasAccessToRoute(route: Route) {
  // by default, everything is public
  let access = true;

  if (route.meta.permission) {
    const { permission } = route.meta;
    const roleMatched = UserModule.hasAccess(permission.role);
    access = !permission.access;
    if (roleMatched) {
      if (typeof permission.access === 'boolean') {
        access = permission.access;
      }
    }
  }

  if (!access) {
    return { access, redirect: '/login' };
  }
  return { access };
}

router.beforeEach((to, from, next) => {
  const { access, redirect } = _hasAccessToRoute(to);
  if (access) {
    next();
  } else {
    next({ path: redirect });
  }
});

export function resetRouter() {
  const newRouter = createRouter();
  (router as any).matcher = (newRouter as any).matcher;
}

export default router;

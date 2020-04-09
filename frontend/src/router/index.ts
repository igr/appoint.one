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
    path: '/404',
    component: () => import('@/views/error-page/404.vue'),
  },
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    children: [
      {
        path: '/home',
        component: () => import('@/views/home/index.vue'),
        name: 'Home',
      },
      {
        path: '/doc/:name',
        props: true,
        component: () => import('@/views/doc/index.vue'),
      },

      {
        path: '/login',
        component: () => import('@/views/login/index.vue'),
      },
      {
        path: '/register',
        component: () => import('@/views/register/index.vue'),
      },
      {
        path: '/register-ok',
        component: () => import('@/views/register-ok/index.vue'),
      },
      {
        path: 'inquire',
        component: () => import('@/views/inquire/index.vue'),
      },
      {
        path: '/appointment/:id',
        props: true,
        component: () => import('@/views/appointment/index.vue'),
      },
      {
        path: '/my',
        component: () => import('@/views/my/index.vue'),
        meta: {
          permission: {
            role: ['DOC'],
            access: true,
          },
        },
      },
      {
        path: '/my/newtime',
        component: () => import('@/views/my-newtime/index.vue'),
        meta: {
          permission: {
            role: ['DOC'],
            access: true,
          },
        },
      },
      {
        path: '/my/appointment/:id',
        props: true,
        component: () => import('@/views/my-appointment/index.vue'),
        meta: {
          permission: {
            role: ['DOC'],
            access: true,
          },
        },
      },
      {
        path: '/evaluation/:id',
        props: true,
        component: () => import('@/views/evaluation/index.vue'),
        meta: {
          permission: {
            role: ['DOC'],
            access: true,
          },
        },
      },

    ],
  },
  {
    path: '/admin',
    component: Layout,
    redirect: '/admin/home',
    meta: {
      permission: {
        role: ['ADMIN'],
        access: true,
      },
    },
    children: [
      {
        path: 'home',
        component: () => import('@/views/admin/index.vue'),
        name: 'Admin',
      },
      {
        path: 'doctors',
        component: () => import('@/views/admin-doctors/index.vue'),
      },
      {
        path: 'user/:id',
        props: true,
        component: () => import('@/views/admin-user/index.vue'),
      },
    ],
  },
  { path: '*', redirect: '/404' },
];

const createRouter = () => new Router({
  mode: 'history',
  routes,
});

const router = createRouter();

function _hasAccessToRoute(route: Route) {
  // by default, everything is public
  let access = true;

  // collect meta from the parents
  let meta: any = {};
  route.matched.forEach((r) => {
    meta = {
      ...meta,
      ...r.meta,
    };
  });

  // detect
  if (meta.permission) {
    const { permission } = meta;
    const roleMatched = UserModule.hasAccess(permission.role);
    access = !permission.access;
    if (roleMatched) {
      if (typeof permission.access === 'boolean') {
        access = permission.access;
      }
    }
  }

  return access;
}

router.beforeEach((to, from, next) => {
  const access = _hasAccessToRoute(to);
  if (access) {
    next();
  } else {
    next({
      path: '/login',
      query: {
        redirect: to.fullPath,
      },
    });
  }
});

export function resetRouter() {
  const newRouter = createRouter();
  (router as any).matcher = (newRouter as any).matcher;
}

export default router;

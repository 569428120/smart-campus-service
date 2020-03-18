// Demo自带的
const testRoutes = [
  // dashboard
  { path: '/', redirect: '/dashboard/analysis' },
  {
    path: '/dashboard',
    name: 'dashboard',
    icon: 'dashboard',
    routes: [
      {
        path: '/dashboard/analysis',
        name: 'analysis',
        component: './Dashboard/Analysis',
      },
      {
        path: '/dashboard/monitor',
        name: 'monitor',
        component: './Dashboard/Monitor',
      },
      {
        path: '/dashboard/workplace',
        name: 'workplace',
        component: './Dashboard/Workplace',
      },
    ],
  },
  //
  // forms
  {
    path: '/form',
    icon: 'form',
    name: 'form',
    routes: [
      {
        path: '/form/basic-form',
        name: 'basicform',
        component: './Forms/BasicForm',
      },
      {
        path: '/form/step-form',
        name: 'stepform',
        component: './Forms/StepForm',
        hideChildrenInMenu: true,
        routes: [
          {
            path: '/form/step-form',
            redirect: '/form/step-form/info',
          },
          {
            path: '/form/step-form/info',
            name: 'info',
            component: './Forms/StepForm/Step1',
          },
          {
            path: '/form/step-form/confirm',
            name: 'confirm',
            component: './Forms/StepForm/Step2',
          },
          {
            path: '/form/step-form/result',
            name: 'result',
            component: './Forms/StepForm/Step3',
          },
        ],
      },
      {
        path: '/form/advanced-form',
        name: 'advancedform',
        authority: ['admin'],
        component: './Forms/AdvancedForm',
      },
    ],
  },
  // list
  {
    path: '/list',
    icon: 'table',
    name: 'list',
    routes: [
      {
        path: '/list/table-list',
        name: 'searchtable',
        component: './List/TableList',
      },
      {
        path: '/list/basic-list',
        name: 'basiclist',
        component: './List/BasicList',
      },
      {
        path: '/list/card-list',
        name: 'cardlist',
        component: './List/CardList',
      },
      {
        path: '/list/search',
        name: 'searchlist',
        component: './List/List',
        routes: [
          {
            path: '/list/search',
            redirect: '/list/search/articles',
          },
          {
            path: '/list/search/articles',
            name: 'articles',
            component: './List/Articles',
          },
          {
            path: '/list/search/projects',
            name: 'projects',
            component: './List/Projects',
          },
          {
            path: '/list/search/applications',
            name: 'applications',
            component: './List/Applications',
          },
        ],
      },
    ],
  },
  {
    path: '/profile',
    name: 'profile',
    icon: 'profile',
    routes: [
      // profile
      {
        path: '/profile/basic',
        name: 'basic',
        component: './Profile/BasicProfile',
      },
      {
        path: '/profile/advanced',
        name: 'advanced',
        authority: ['admin'],
        component: './Profile/AdvancedProfile',
      },
    ],
  },
  {
    name: 'result',
    icon: 'check-circle-o',
    path: '/result',
    routes: [
      // result
      {
        path: '/result/success',
        name: 'success',
        component: './Result/Success',
      },
      { path: '/result/fail', name: 'fail', component: './Result/Error' },
    ],
  },
  {
    name: 'exception',
    icon: 'warning',
    path: '/exception',
    routes: [
      // exception
      {
        path: '/exception/403',
        name: 'not-permission',
        component: './Exception/403',
      },
      {
        path: '/exception/404',
        name: 'not-find',
        component: './Exception/404',
      },
      {
        path: '/exception/500',
        name: 'server-error',
        component: './Exception/500',
      },
      {
        path: '/exception/trigger',
        name: 'trigger',
        hideInMenu: true,
        component: './Exception/TriggerException',
      },
    ],
  },
  {
    name: 'account',
    icon: 'user',
    path: '/account',
    routes: [
      {
        path: '/account/center',
        name: 'center',
        component: './Account/Center/Center',
        routes: [
          {
            path: '/account/center',
            redirect: '/account/center/articles',
          },
          {
            path: '/account/center/articles',
            component: './Account/Center/Articles',
          },
          {
            path: '/account/center/applications',
            component: './Account/Center/Applications',
          },
          {
            path: '/account/center/projects',
            component: './Account/Center/Projects',
          },
        ],
      },
      {
        path: '/account/settings',
        name: 'settings',
        component: './Account/Settings/Info',
        routes: [
          {
            path: '/account/settings',
            redirect: '/account/settings/base',
          },
          {
            path: '/account/settings/base',
            component: './Account/Settings/BaseView',
          },
          {
            path: '/account/settings/security',
            component: './Account/Settings/SecurityView',
          },
          {
            path: '/account/settings/binding',
            component: './Account/Settings/BindingView',
          },
          {
            path: '/account/settings/notification',
            component: './Account/Settings/NotificationView',
          },
        ],
      },
    ],
  },
];

// 智慧校园的路由
const appRoutes = [
  { path: '/', redirect: '/workplace' },
  {
    path: '/workplace',
    component: './Portal/Workplace',
  },
  // 系统设置
  {
    path: '/sys-manage',
    name: 'sys-manage',
    //   icon: 'dashboard',
    routes: [
      // 租户管理
      {
        path: '/sys-manage/tenant-manage',
        name: 'tenant-manage',
        routes: [
          {
            path: '/sys-manage/tenant-manage/region',
            name: 'region',
            component: './SystemManage/Region',
          },
          {
            path: '/sys-manage/tenant-manage/school',
            name: 'school',
            component: './SystemManage/School',
          },
        ],
      },
      // 权限管理
      {
        path: '/sys-manage/authority-manage',
        name: 'authority-manage',
        routes: [
          {
            path: '/sys-manage/authority-manage/pc-menu',
            name: 'pc-menu',
            component: './SystemManage/PcMenu',
          },
          {
            path: '/sys-manage/authority-manage/app-menu',
            name: 'app-menu',
            component: './SystemManage/AppMenu',
          },
          {
            path: '/sys-manage/authority-manage/authority-group',
            name: 'authority-group',
            component: './Dashboard/Monitor',
          },
        ],
      },
    ],
  },
  // 人员管理
  {
    path: '/personnel-manage',
    name: 'personnel-manage',
    routes: [
      // 职工管理
      {
        path: '/personnel-manage/staff-manage',
        name: 'staff-manage',
        routes: [
          {
            path: '/personnel-manage/staff-manage/staff-group',
            name: 'staff-group',
            component: './Dashboard/Analysis',
          },
          {
            path: '/personnel-manage/staff-manage/staff-user',
            name: 'staff-user',
            component: './Dashboard/Monitor',
          },
        ],
      },
      // 学生管理
      {
        path: '/personnel-manage/student-manage',
        name: 'student-manage',
        routes: [
          {
            path: '/personnel-manage/student-manage/student-group',
            name: 'student-group',
            component: './Dashboard/Analysis',
          },
          {
            path: '/personnel-manage/student-manage/student',
            name: 'student',
            component: './Dashboard/Monitor',
          },
        ],
      },
    ],
  },
  //门禁管理
  {
    path: '/access-control',
    name: 'access-control',
    routes: [
      // 门禁权限
      {
        path: '/access-control/access-authority',
        name: 'access-authority',
        routes: [
          {
            path: '/access-control/access-authority/strategy',
            name: 'strategy',
            component: './Dashboard/Analysis',
          },
          {
            path: '/access-control/access-authority/control',
            name: 'control',
            component: './Dashboard/Monitor',
          },
          {
            path: '/access-control/access-authority/examine',
            name: 'examine',
            component: './Dashboard/Monitor',
          },
        ],
      },
      // 出入记录
      {
        path: '/access-control/access-record',
        name: 'access-record',
        routes: [
          {
            path: '/access-control/access-record/personnel',
            name: 'personnel',
            component: './Dashboard/Analysis',
          },
          {
            path: '/access-control/access-record/car',
            name: 'car',
            component: './Dashboard/Monitor',
          },
          {
            path: '/access-control/access-record/statistics',
            name: 'statistics',
            component: './Dashboard/Monitor',
          },
        ],
      },
    ],
  },
  // 信息录入
  {
    path: '/info-entry',
    name: 'info-entry',
    routes: [
      // 人员特征
      {
        path: '/info-entry/personnel-feature',
        name: 'personnel-feature',
        routes: [
          {
            path: '/info-entry/personnel-feature/card',
            name: 'card',
            component: './Dashboard/Analysis',
          },
          {
            path: '/info-entry/personnel-feature/face',
            name: 'face',
            component: './Dashboard/Monitor',
          },
        ],
      },
    ],
  },
  // 智能设备
  {
    path: '/device-manage',
    name: 'device-manage',
    routes: [
      // 门禁设备
      {
        path: '/device-manage/access-device',
        name: 'access-device',
        routes: [
          {
            path: '/device-manage/access-device/gate',
            name: 'gate',
            component: './Dashboard/Analysis',
          },
          {
            path: '/device-manage/access-device/car-gate',
            name: 'car-gate',
            component: './Dashboard/Monitor',
          },
        ],
      },
    ],
  },
];

export default [
  // user
  {
    path: '/user',
    component: '../layouts/UserLayout',
    routes: [
      { path: '/user', redirect: '/user/login' },
      { path: '/user/login', component: './User/Login' },
      { path: '/user/register', component: './User/Register' },
      { path: '/user/register-result', component: './User/RegisterResult' },
    ],
  },
  // app
  {
    path: '/',
    component: '../layouts/BasicLayout',
    Routes: ['src/pages/Authorized'],
    // authority: ['admin', 'user'],
    routes: [
      // ...testRoutes,
      ...appRoutes,
      {
        component: '404',
      },
    ],
  },
];

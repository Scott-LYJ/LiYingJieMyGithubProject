
/**
 * 这里配置开发环境写死的路由和菜单，方便开发
 */
//静态路由
var routeDevList = [
    {
        path: '/routedev',
        component: resolve => require(['../components/admin/UserEdit'], resolve),
        meta: {
            title: '个人信息修改'
        }
    },
  {
    path: '/groupAndapi',
    component: resolve => require(['../components/project/groupAndapi'], resolve),
    meta: {
      title: '分组/接口'
    }
  },
  {
    path: '/api',
    component: resolve => require(['../components/project/api'], resolve),
    meta: {
      title: '接口'
    }
  },
  {
    path: '/TestInterface',
    component: resolve => require(['../components/interfacemanager/TestInterface/TestInterface.vue'], resolve),
    meta: {
      title: '接口测试'
    }
  },
];
//静态菜单
var menuDevList = [
    {
        icon: 'el-icon-date',
        index: Math.random()+'',
        title: '个人管理',
        subs: [
            {
                index: 'routedev',
                title: '个人信息修改'
            },
        ]
    }
]


var routeDev = {
    ROUTE_DEV: true,//是否写入静态路由（开关）
    routeDevList: routeDevList,
    MENU_DEV: true,//是否写入静态菜单（开关）
    menuDevList: menuDevList,
}
export default routeDev;

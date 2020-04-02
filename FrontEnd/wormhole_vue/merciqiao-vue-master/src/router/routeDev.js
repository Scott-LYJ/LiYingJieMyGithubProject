
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
    path: '/apidocumentdetail',
    component: resolve => require(['../components/apidocument/apiDocumentDetail'], resolve),
    meta: {
      title: '接口文档信息'
    }

  },
  {
    path: '/apidocumentdetail2',
    component: resolve => require(['../components/test/apiDocumentDetail'], resolve),
    meta: {
      title: '接口文档信息2'
    }

  },
  {
    path: '/echats',
    component: resolve => require(['../components/test/echats'], resolve),
    meta: {
      title: 'echarts'
    }

  },
  {
    path: '/pinglun',
    component: resolve => require(['../components/test/ArticleComment.vue'], resolve),
    meta: {
      title: 'pinglun'
    }
  },
  {
    path: '/testWebSocket',
    component: resolve => require(['../components/test/testWebSocket.vue'], resolve),
    meta: {
      title: 'test'
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
          {
            index: 'apidocumentdetail',
            title: '接口文档信息'
          },
          {
            index: 'apidocumentdetail2',
            title: '接口文档信息'
          },
          {
            index: 'pinglun',
            title: '评论'
          },
          {
            index: 'testWebSocket',
            title: 'test'
          },
          {
            index: 'echats',
            title: 'echarts'
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

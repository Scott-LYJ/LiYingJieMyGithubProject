/**
 *
 * 懒加载动态路由
 */
function lazy(code) {

  //return  resolve => require(["@/components/" + name + ".vue"], resolve)
  return getComponetByPath('/' + code);


}
function getComponetByPath(path) {
  for (var i = 0; i < dynamicRouter.length; i++) {
    var route = dynamicRouter[i];
    if (route.path == path) {
      return route.component;
    }
  }
}
/**
 * 路由定义
 * path跟配置里的资源标示对应,同时跟url地址对应
 */
const dynamicRouter = [
  {
    name: 'index',
    path: '/index',
    component: resolve => require(['@/components/index.vue'], resolve),
    meta: {
      title: '系统首页'
    }
  },
  {
    name: 'messageboard',
    path: '/messageboard',
    component: resolve => require(['@/components/tablepage/messageboard.vue'], resolve),
    meta: {
      title: '系统首页'
    }
  },
  {
    name: 'searchinput',
    path: '/searchinput',
    component: resolve => require(['@/components/searchinput/searchinput.vue'], resolve),
    meta: {
      title: '查询输入页'
    }
  },
  {
    name: 'groupAndapi',
    path: '/groupAndapi',
    component: resolve => require(['@/components/project/groupAndapi.vue'], resolve),
    meta: {
      title: '标签选项卡'
    }
  },
  {
    name: 'tablepage',
    path: '/tablepage',
    component: resolve => require(['@/components/tablepage/tablepage.vue'], resolve),
    meta: {
      title: '综合表格页'
    }
  }
  ,
  {
    name: 'formpage',
    path: '/formpage',
    component: resolve => require(['@/components/formpage/formpage.vue'], resolve),
    meta: {
      title: '表单页'
    }
  }
  ,
  {
    name: 'treepage',
    path: '/treepage',
    component: resolve => require(['@/components/treepage/treepage.vue'], resolve),
    meta: {
      title: '树组件页'
    }
  },
  {
    name: 'OrganizationTree',
    path: '/organizationTree',
    component: resolve => require(['@/components/admin/OrganizationTree.vue'], resolve),
    meta: { title: '机构管理' }
  },
  {
    name: 'SysUser',
    path: '/sysUser',
    component: resolve => require(['@/components/admin/SysUser.vue'], resolve),
    meta: { title: '用户管理' }
  },  {
    name: 'SysUser',
    path: '/sysUser1',
    component: resolve => require(['@/components/admin/SysUser1.vue'], resolve),
    meta: { title: '用户管理' }
  },
  {
    name: 'SysAcl',
    path: '/sysAcl',
    component: resolve => require(['@/components/admin/SysAcl.vue'], resolve),
    meta: { title: '角色资源管理' }
  },
  {
    name: 'SysMenu',
    path: '/sysMenu',
    component: resolve => require(['@/components/admin/SysMenu.vue'], resolve),
    meta: { title: '菜单管理' }
  },
  {
    name: 'SysResource',
    path: '/sysResource',
    component: resolve => require(['@/components/admin/SysResource.vue'], resolve),
    meta: { title: '资源管理' }
  },
  {
    name: 'SysRole',
    path: '/sysRole',
    component: resolve => require(['@/components/admin/SysRole.vue'], resolve),
    meta: { title: '角色管理' }
  },
  {
    name: 'sysRoleMap',
    path: '/sysRoleMap',
    component: resolve => require(['@/components/admin/SysRoleMap.vue'], resolve),
    meta: { title: '角色权限管理' }
  },
  {
    name: 'GatewayApiGroup',
    path: '/GatewayApiGroup',
    component: resolve => require(['@/components/project/GatewayApiGroup.vue'], resolve),
    meta: { title: '分组管理' }
  },
  {
    name: 'GroupDetail',
    path: '/GroupDetail',
    component: resolve => require(['@/components/project/GroupDetail.vue'], resolve),
    meta: { title: '分组管理' }
  },
  {
    name: 'InterfaceManager',
    path: '/InterfaceManager',
    component: resolve => require(['@/components/interfacemanager/InterfaceManage/InterfaceManager.vue'], resolve),
    meta: { title: '接口管理' }
  },
  {
    name: 'api',
    path: '/api',
    component: resolve => require(['@/components/project/api.vue'], resolve),
    meta: { title: '接口' }
  },
  {
    name: 'msg',
    path: '/msg',
    component: resolve => require(['@/components/message/msg.vue'], resolve),
    meta: { title: '建议留言' }
  },
  {
    name: 'gatewayproject',
    path: '/gatewayproject',
    component: resolve => require(['@/components/project/GatewayProject.vue'], resolve),
    meta: {
      title: '项目管理'
    }
  },
  {
    name: 'messageboard2',
    path: '/messageboard2',
    component: resolve => require(['@/components/tablepage/messageboard2.vue'], resolve),
    meta: {
      title: '增删改查二'
    }
  },
  {
    name: 'transition',
    path: '/transition',
    component: resolve => require(['@/components/flex/transition.vue'], resolve),
    meta: {
      title: '左导航切换效果'
    }
  },
  {
    name: 'detail',
    path: '/detail',
    component: resolve => require(['@/components/tablepage/detail.vue'], resolve),
    meta: { title: '详情页' }
  },
  {
    name: 'ckeditor',
    path: '/ckeditor',
    component: resolve => require(['@/components/ckeditor/index.vue'], resolve),
    meta: { title: '文本编辑器' }
  },
  {
    name: 'yanshi',
    path: '/yanshi',
    component: resolve => require(['@/components/yanshi/yanshi.vue'], resolve),
    meta: { title: '使用演示' }
  },
  {
    path: '/honor',
    component: resolve => require(['@/components/other/honor'], resolve),
    meta: {
      title: '小荣誉'
    }
  },
]
export { lazy }


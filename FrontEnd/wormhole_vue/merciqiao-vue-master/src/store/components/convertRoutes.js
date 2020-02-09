import {lazy} from './lazyLoading'
/**
 * 拼接动态路由
 */
export default (routerList,routeHideList, sysMenuList) => {
    toRoutes(routerList,routeHideList, sysMenuList)
    //toRoutes2(routerList,routeHideList, sysMenuList)
}
function toRoutes(routerList,routeHideList, sysMenuList) {
    if (sysMenuList) {
        for (var i = 0; i < sysMenuList.length; i++) {
            var sysMenuNode = sysMenuList[i];
            if (sysMenuNode.resoure_type == 'url') {
                var code=sysMenuNode.permission;
                var routeNode = {
                    name: code,
                    path: '/' + code,
                    component: lazy(code),
                    meta: { title: sysMenuNode.menuName },
                    children:[]
                };
                 //子节点存在
                if (sysMenuNode.sysMenuVoChild && sysMenuNode.sysMenuVoChild.length > 0) {
                    toRoutes(routeNode.children,routeHideList,sysMenuNode.sysMenuVoChild);
                }
                if(code&&code!=''){
                    if(sysMenuNode.isShow=='1'){
                        routerList.push(routeNode);
                    }
                    else{
                        routeHideList.push(routeNode);
                    }
                }
            }
            else{
                 //子节点存在
                if (sysMenuNode.sysMenuVoChild && sysMenuNode.sysMenuVoChild.length > 0) {
                    toRoutes(routerList,routeHideList,sysMenuNode.sysMenuVoChild);
                }
            }
        }
    }
}


//test
function toRoutes2(routerList,routeHideList, sysMenuList) {
  if (sysMenuList) {
    for (var i = 0; i < sysMenuList.length; i++) {

      var sysMenuNode = sysMenuList[i];
      console.log("执行----------------111"+sysMenuNode)
      if (sysMenuNode.category == 'm') {
        var code=sysMenuNode.perm;
        var routeNode = {
          name: code,
          path: '/' + code,
          component: lazy(code),
          meta: { title: sysMenuNode.name },
          children:[]
        };
        console.log("component+++++++++++"+lazy(code));
        //子节点存在
        if (sysMenuNode.authResourceList && sysMenuNode.authResourceList.length > 0) {
          toRoutes2(routeNode.children,routeHideList,sysMenuNode.authResourceList);
        }
        if(code&&code!=''){
          if(sysMenuNode.isShow=='1'){
            routerList.push(routeNode);
          }
          else{
            routeHideList.push(routeNode);
          }
        }
      }
      else{
        //子节点存在
        if (sysMenuNode.authResourceList && sysMenuNode.authResourceList.length > 0) {
          toRoutes2(routerList,routeHideList,sysMenuNode.authResourceList);
        }
      }
    }
  }
}

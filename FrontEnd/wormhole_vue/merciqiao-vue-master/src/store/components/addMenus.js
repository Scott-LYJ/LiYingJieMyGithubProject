import routeDev from '../../router/routeDev'
/**
 * 动态添加菜单
 * author:qlx
 */
const addMenus = {
  state: {
    menuList: []
  },
  getters:{
    getMenuList:state=>{
      if(routeDev.MENU_DEV==true){
        return state.menuList.concat(routeDev.menuDevList);
      }
      else{
        return state.menuList;
      }

  }
  },
  mutations: {
    add_Menus(state, param) {
        if(param){
            var menuList=[];
            _addMenu(menuList,param);
         //addMenu2(menuList,param);
             state.menuList=menuList;
             console.log("执行add_Menus")
        }
        function _addMenu(menuList,params){

            for(var i=0;i<params.length;i++){
                 var menu={
                        icon: 'el-icon-tickets',
                        index: '1',
                        title: '工作台',
                        subs: []
                };
                var menuParam=params[i];
                 if(menuParam.isShow==0){
                    continue;
                }
                menu.icon=menuParam.menuIcon;
                // menu.index=menuParam.orderBy;
                menu.index=menuParam.permission;
                menu.title=menuParam.menuName;
                if(menuParam.sysMenuVoChild&&menuParam.sysMenuVoChild.length>0){
                    _addMenu(menu.subs,menuParam.sysMenuVoChild);
                }
                menuList.push(menu);

            }
          console.log("测试"+menuList)
        }
      function addMenu2(menuList,params){

        for(var i=0;i<params.length;i++){
          var menu={
            icon: 'el-icon-tickets',
            index: '1',
            title: '工作台',
            subs: []
          };
          var menuParam=params[i];
          if(menuParam.isShow==0){
            continue;
          }
          menu.icon=menuParam.icon;
          // menu.index=menuParam.orderBy;
          menu.index=menuParam.perm;
          menu.title=menuParam.name;
          if(menuParam.authResourceList&&menuParam.authResourceList.length>0){
            addMenu2(menu.subs,menuParam.authResourceList);
          }
          menuList.push(menu);

        }
        console.log("测试"+menuList)
      }
    }
  },
  actions: {
    add_Menus({commit}, param) {
      commit('add_Menus', param)
    }
  }
}
export default addMenus

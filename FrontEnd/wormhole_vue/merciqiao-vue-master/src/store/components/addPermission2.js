import routeDev from '../../router/routeDev'
/**
 * 动态添加菜单
 * author:qlx
 */
const addPermission2 = {
  state: {
    btnPermissions:[]
  },
  getters:{
    btnPermissions:state=>{
      return state.btnPermissions;
    }
  },
  mutations: {
    add_Permission2(state,param){
      if(param){
        var permissionList=[];
        //_addMenu(menuList,param);
        addToPermission(permissionList,param);
        state.btnPermissions=permissionList;
        console.log("执行add_Permission",permissionList)
      }
      function addToPermission(permissionList,params){
        for(var i=0;i<params.length;i++) {
          var permission = {
            name: '',
            url: '',
            subs: []
          };
          var menuParam = params[i];
          if (menuParam.isShow!= 0) {
            continue;
          }

          permission.name = menuParam.name;
          permission.url = menuParam.url;
          if (menuParam.sysMenuVoChild && menuParam.sysMenuVoChild.length > 0) {
            addToPermission(permission.subs, menuParam.sysMenuVoChild);
          }
          permissionList.push(permission);

        }


      }
    },
  },
  actions: {
    add_Permission2({commit}, param) {
      console.log("djaskjfljfijsadflkjalk")
      commit('add_Permission2', param)
    }
  }
}
export default addPermission2

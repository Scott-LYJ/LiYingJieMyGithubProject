const addPermission={
  state:{
    btnPermissions:[]
  },
  getters:{
    btnPermissions:state=>{
      return state.btnPermissions;
    }
  },
  mutations:{
    add_Permissions(state,param){
      state.btnPermissions=param;
    }
  },
  actions:{
    add_Permissions({commit},param){
      console.log(param)
      var permissionList=[];
      if(param&&param.length>0){
        for(var i=0;i<param.length;i++){
          permissionList.push(param[i].uri);
        }

      }
      console.log(permissionList)
      commit('add_Permissions',permissionList);
    }
  }

}
export default addPermission;

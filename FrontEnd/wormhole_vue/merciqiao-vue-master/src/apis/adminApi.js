import Axios from "axios";
/**
 * 提供组织,用户,角色等相关接口
 */
export default {
    add:function(){
        alert('add');
    },
    remove:function(){
        alert('remove');
    },
    querySysUserList:function ({skip,size,name,id,cdt,phone}) {
      var param = {
        skip,
        size,
        name,
        id,
        cdt,
        phone
      }
      return Axios.post('/user/querySysUserList', param);
    }
}

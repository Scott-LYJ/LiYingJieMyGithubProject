import Axios from "axios";

/**
 * 提供msg相关接口
 */
export default {
    add:function({name,id,createrid,picture,des}){
         var param={
            name:name,
            id:id,
            createrid:createrid,
            avatar:picture,
            des:des,
        };
        return Axios.post('/project/addProject', param);
    },
    update:function({id,name,createrid,picture,des}){
        var param={
          name:name,
          id:id,
          createrid:createrid,
          avatar:picture,
          des:des,
       };
       return Axios.post('/project/updateProject', param);
   },
   delete:function({id}){
        var param={
            id:id
        };
        return Axios.post('/project/delete', param);
    },
    deleteBatch:function({ids}){
        var param={
            ids:ids
        };
        return Axios.post('/project/deleteBatch', param);
    },
    getList({id,name,cdt,createrid,currentPage,pageSize}){
        var param={
            name:name,
            id:id,
            cdt:cdt,
            createrid:createrid,
            skip:(currentPage-1)*pageSize,
            size:pageSize
        };
        return Axios.post('/project/queryProjectList', param);
    }
}

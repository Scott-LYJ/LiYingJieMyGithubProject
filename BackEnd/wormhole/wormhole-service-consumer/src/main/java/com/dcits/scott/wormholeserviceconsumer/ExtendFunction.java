package com.dcits.scott.wormholeserviceconsumer;

import com.dcits.scott.admin.pojo.*;
import com.dcits.scott.support.result.Info;
import com.dcits.scott.support.result.ResultInfo;
import com.dcits.scott.auth.authauthorization.AuthorizationService;
import com.dcits.scott.auth.authpermission.PermissionService;
import com.dcits.scott.auth.authresource.ResourceService;
import com.dcits.scott.auth.authrole.RoleService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExtendFunction {
  public   static Map<String,Object> map(AuthorizationService authorizationService,
                                         PermissionService permissionService,
                                         ResourceService resourceService,
                                         RoleService roleService){

        Map<String, Object> map1 = new HashMap<>(8);
        Set<Integer> set = new HashSet<>();

        List<Integer> roleIds = new ArrayList<>();
        List<Integer> resourceIds = new ArrayList<>();

        List<AuthResource> resourceList;
        List<AuthPermission> permissionList;
        List<AuthAuthorization> authorizationList;

        ResultInfo resultInfo = new ResultInfo();
        Info info;
        try {

            Object key =  SecurityUtils.getSubject().getPrincipal();
            AuthUser user=new AuthUser();
            try {
                BeanUtils.copyProperties(user, key);
            } catch (Exception e) {

            }
            authorizationList = authorizationService.selectByUserId(user.getId());
            for (AuthAuthorization authorization : authorizationList) {
                roleIds.add(authorization.getRoleId());
            }

            map1.put("roleIds", roleIds);
          //  List<AuthRole> authRoleList = roleService.selectList(map1);
            List<AuthRole> authRoleList = roleService.queryRoleUserList(roleIds);
           // permissionList = permissionService.selectList(map1);
            permissionList = permissionService.querPermissionList(roleIds);
            for (AuthPermission permission : permissionList) {
                resourceIds.add(permission.getResId());
            }
            set.addAll(resourceIds);
            resourceIds.clear();
            resourceIds.addAll(set);

            map1.clear();
            map1.put("resourceIds", resourceIds);

            resourceList = resourceService.selectList(map1);
          //  List<Menu> menuList = resourceService.selectList(map1);
            map1.put("resourceList", resourceList);
            map1.clear();
           //JSONObject jsonObject =  queryMenuList(resourceList);
           resourceList= queryMenuList(resourceList);
          // map1.put("Menu",jsonObject);

           info = new Info(user,authRoleList,resourceList);
           map1.clear();
           Map<String,Info> resultMap = new HashMap<>();
            resultMap.put("data",info);
           resultInfo = new ResultInfo("SUCCESS","登录成功",null,resultMap,null,"");
           map1.put("result",resultInfo);

        } catch (Exception e) {
            e.printStackTrace();
//            log.error(e.getLocalizedMessage(), e);
        }
        return map1;
    }

    public static List<AuthResource> queryMenuList(List<AuthResource> rootResource){

        // 查看结果
        for (AuthResource authResource : rootResource) {
            System.out.println(authResource);
        }
        //最终结果
        List<AuthResource> menuList  = new ArrayList<AuthResource>();
        // 先找到所有的一级菜单
        for (int i = 0; i < rootResource.size(); i++) {
            // 一级菜单没有parentId
            if (0==(rootResource.get(i).getPid())) {
                menuList.add(rootResource.get(i));
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (AuthResource authResource : menuList) {
            authResource.setChildren(getChild(authResource.getId(), rootResource));
        }
//        Map<String,Object> jsonMap = new HashMap<>();
//        jsonMap.put("menu", menuList);
//        JSONObject json = new JSONObject(jsonMap);
//        System.out.println(json);
        return menuList;
    }


       /*
      递归查找子菜单
      @param id
      当前菜单id
      @param rootMenu
      要查找的列表
      @return
             */
    private static List<AuthResource> getChild(Integer id, List<AuthResource> rootMenu) {
        // 子菜单
        List<AuthResource> childList = new ArrayList<>();
        for (AuthResource menu : rootMenu) {
            if ("a".equals(menu.getCategory())){
                menu.setIsShow(0);
            }else menu.setIsShow(1);
            // 遍历所有节点，将父菜单id与传过来的id比较
                if (menu.getPid().equals(id)) {
                    childList.add(menu);
                }
        }
        // 把子菜单的子菜单再循环一遍
        for (AuthResource menu : childList) {// 没有url子菜单还有子菜单

                if (!"a".equals(menu.getCategory())){
                // 递归
                menu.setChildren(getChild(menu.getId(), rootMenu));
            }
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    public static String changeDate(Object object){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Long cdt = Long.parseLong(String.valueOf(object));

        String d = format.format(cdt);
        Date date= null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Format To String(Date):"+d);
        System.out.println("Format To Date:"+date);
      return d;
    }
}

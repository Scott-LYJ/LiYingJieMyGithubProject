package com.dcits.scott.wormholeserviceconsumer.config.shiroconfig;

import com.dcits.scott.admin.pojo.AuthAuthorization;
import com.dcits.scott.admin.pojo.AuthPermission;
import com.dcits.scott.admin.pojo.AuthResource;
import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.auth.authauthorization.AuthorizationService;
import com.dcits.scott.auth.authpermission.PermissionService;
import com.dcits.scott.auth.authresource.ResourceService;
import com.dcits.scott.auth.authuser.AuthUserService;
import com.dcits.scott.util.ShiroEncryption;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.LoggerFactory;

import java.util.*;

//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserRealm.class);

    //引入userService
    @Reference
    AuthUserService authUserService;
    //引入角色和用户关联
    @Reference
    AuthorizationService authorizationService;
    //引入权限
    @Reference
    PermissionService permissionService;
    //引入资源
    @Reference
    ResourceService resourceService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("-------------授权----------------");
        if (principalCollection == null) {
            throw new AuthorizationException("principalCollection is null");
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        try {
            Map<String, Object> map = new HashMap<String, Object>(8);
            List<Integer> roleIds = new ArrayList<Integer>();
            String name = getName();
         //   AuthUser user = (AuthUser) principalCollection.fromRealm(getName()).iterator().next();
         //   AuthUser user =(AuthUser) principalCollection.getPrimaryPrincipal();
            Object key = principalCollection.getPrimaryPrincipal();
            AuthUser user=new AuthUser();
            try {
                BeanUtils.copyProperties(user, key);
            } catch (Exception e) {

            }
            map.put("userId", user.getId());
            List<AuthAuthorization> authAuthorizations1 = authorizationService.selectByUserId(20);
            System.out.println("     "+authAuthorizations1);
            List<AuthAuthorization> authAuthorizations = authorizationService.selectList(map);
            System.out.println("-----"+authAuthorizations);
//            AuthAuthorization authAuthorization = (AuthAuthorization) authAuthorizations.get(0);
//            roleIds.add(authAuthorization.getRoleId());
            for (AuthAuthorization authorization : authAuthorizations) {
                roleIds.add(authorization.getRoleId());
            }
            List<Integer> resourceIds = new ArrayList<Integer>();
            map.clear();
            map.put("roleIds", roleIds);
            Iterator<?> iterator = permissionService.selectList(map).iterator();
            while (iterator.hasNext()) {
                resourceIds.add(((AuthPermission) (iterator.next())).getResId());
            }
            Object set = new HashSet<Integer>(resourceIds);
            resourceIds.clear();
            resourceIds.addAll((Collection<Integer>) set);

            map.clear();
            map.put("resourceIds", resourceIds);
            List<AuthResource> resources = this.resourceService.selectList(map);
            for (AuthResource resource : resources) {
                if (!StringUtils.isBlank(resource.getUri())) {
                    info.addStringPermission(resource.getUri());
                }
            }
        } catch (Exception e) {
            logger.error("authorization fail. reason is {}", e);
        }
        logger.info("---- 获取到以下权限 ----");
        logger.info(String.valueOf(info.getStringPermissions()));
        logger.info("---------------- Shiro 权限获取成功 ----------------------");
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException  {
        System.out.println("-------------认证----------------");
        //获取tokean的用户名和密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String loginName = token.getUsername().trim();
        String password = String.valueOf(token.getPassword()).trim();
        //初始化user对象
        AuthUser authUser = new AuthUser();
        if (null == authUser) {
            throw new AccountException("帐号或密码不正确！");
        }
        authUser.setName(loginName);
        //查询数据库
        AuthUser oringnAuthUser = authUserService.loginAuthentication(authUser);
        //盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(authUser.getName());
        System.out.println(oringnAuthUser.toString());
        return new SimpleAuthenticationInfo(oringnAuthUser,oringnAuthUser.getPassword(),credentialsSalt,getName());
    }
    //md5加密测试
    public static void main(String[] args) {
        String hashAlgorithName = "MD5";
        String password = "123456";
        int hashIterations = 1024;//加密次数
        ByteSource credentialsSalt = ByteSource.Util.bytes("root");
        Object obj = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
        System.out.println(obj);
    }
}

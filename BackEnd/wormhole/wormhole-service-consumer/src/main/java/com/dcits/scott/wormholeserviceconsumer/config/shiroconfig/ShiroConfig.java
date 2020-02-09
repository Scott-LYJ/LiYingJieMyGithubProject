package com.dcits.scott.wormholeserviceconsumer.config.shiroconfig;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //ShrioFilterFactoryBean+>shirofilter
    @Bean
    public ShiroFilterFactoryBean getShiroFilter(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {

        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilter.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器
        /*
        anon:无验证就可以访问
        authc:需认证才能访问
        user:必须拥有记住我才能用
        perms:拥有对某个资源的权限才能访问
        role:拥有某个角色权限才能访问
                 */
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/hello", "anon");
//        filterMap.put("/login", "anon");
        filterMap.put("/loginn", "anon");
       // filterMap.put("/*", "authc");
        // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了, 位置放在 anon、authc下面
        filterMap.put("/logout", "logout");

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        // 配器shirot认登录累面地址，前后端分离中登录累面跳转应由前端路由控制，后台仅返回json数据, 对应LoginController中unauth请求
        shiroFilter.setLoginUrl("/un_auth");

        // 登录成功后要跳转的链接, 此项目是前后端分离，故此行注释掉，登录成功之后返回用户基本信息及token给前端
        // shiroFilterFactoryBean.setSuccessUrl("/index");

        // 未授权界面, 对应LoginController中 unauthorized 请求
        shiroFilter.setUnauthorizedUrl("/unauthorized");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    //DefauleWebSecurityManager
    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userRealm);
        // 自定义缓存实现 使用redis
        defaultWebSecurityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        defaultWebSecurityManager.setSessionManager(sessionManager());
        return defaultWebSecurityManager;
    }
    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }
    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("192.168.81.128");
        redisManager.setPort(6379);
        redisManager.setPassword("li774143844");
        redisManager.setExpire(1800);// 配置缓存过期时间
        redisManager.setTimeout(0);
        // redisManager.setPassword(password);
        return redisManager;
    }
    /**
     * My Session Manager
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        MySessionManager sessionManager = new MySessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }
    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    //创建realm对象需要自定义
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }
    @Bean("userRealm")
    @DependsOn("lifecycleBeanPostProcessor")//可选
    public UserRealm userRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setAuthorizationCachingEnabled(false);
        userRealm.setCredentialsMatcher(matcher);
        return userRealm;
    }
    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /**
     * 限制同一账号登录同时登录人数控制
     *
     * @return
     */
//    @Bean
//    public KickoutSessionControlFilter kickoutSessionControlFilter() {
//        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
//        kickoutSessionControlFilter.setCacheManager(cacheManager());
//        kickoutSessionControlFilter.setSessionManager(sessionManager());
//        kickoutSessionControlFilter.setKickoutAfter(false);
//        kickoutSessionControlFilter.setMaxSession(1);
//        kickoutSessionControlFilter.setKickoutUrl("/auth/kickout");
//        return kickoutSessionControlFilter;
//    }
    /***
     * 授权所用配置
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
    /**
     * Shiro生命周期处理器
     *
     */
    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {

        return new LifecycleBeanPostProcessor();
    }
    /**
     * Session ID 生成器
     *
     * @return JavaUuidSessionIdGenerator
     */
    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public SimpleCookie cookie() {
        // cookie的name,对应的默认是 JSESSIONID
        SimpleCookie cookie = new SimpleCookie("SHARE_JSESSIONID");
        cookie.setHttpOnly(true);
        //  path为 / 用于多个系统共享 JSESSIONID
        cookie.setPath("/");
        return cookie;
    }



}

package com.dcits.scott.wormholeserviceconsumer.config.shiroconfig;

import com.alibaba.fastjson.JSON;
import com.dcits.scott.admin.pojo.AuthUser;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

/**
 * 请求检查是否带着token
 */
public class KickoutSessionControlFilter extends AccessControlFilter {
    @Autowired
    RedisSessionDAO redisSessionDAO;

    private String kickoutUrl; //踢出后到的地址
    private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
    private int maxSession = 1; //同一个帐号最大会话数 默认1

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    //设置Cache的key的前缀
    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro_redis_cache");
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//

        String requestURI = WebUtils.toHttp(request).getRequestURI();
        if ("/login".equals(requestURI)) {
            return true;
        }
        if ("/logout".equals(requestURI)) {
            return true;
        }
        if ("/test".equals(requestURI)) {
            return true;
        }
        if ("/login?message=你的账号已在其他地方登陆,或超时。请重新登陆".equals(requestURI)) {
            return true;
        }
        if ("/websocket".equals(requestURI.substring(0, 10))) {
            return true;
        }
        //如果请求头中有 Authorization 则其值为sessionId
        String id = WebUtils.toHttp(request).getHeader("token");
        //如果请求头中有 Authorization 则其值为sessionId
        if (!StringUtils.isEmpty(id)) {
            String requestDispatcherPath = (String) request.getAttribute("requestDispatcherPath");


            Collection<Session> activeSessions = redisSessionDAO.getActiveSessions();
            Iterator<Session> it = activeSessions.iterator();
            while (it.hasNext()) {                                       // session 进行遍历
                Session session = it.next();
                String id1 = (String) session.getId();
//                String id1 =(String) session.getAttribute("id");
                if (id.equals(id1)) {
                    return true;
                }
            }


        }
        response.getWriter().print("error");
        return false;



    }

}

package com.example.wry_springboot.config;

import com.example.wry_springboot.Util.ResponseResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;

public class KickoutSessionFilter extends AccessControlFilter {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    private String kickoutUrl; // 踢出后到的地址
    private boolean kickoutAfter = false; // 踢出之前登录的/之后登录的用户 默认false踢出之前登录的用户
    private int maxSession = 1; // 同一个帐号最大会话数 默认1
    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;
    public void setKickoutUrl(String kickoutUrl){
        this.kickoutUrl =kickoutUrl;
    }
    public void setKickoutAfter(boolean kickoutAfter){
        this.kickoutAfter = kickoutAfter;
    }
    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    // 设置Cache的key的前缀
    public void setCacheManager(CacheManager cacheManager) {
        //必须和ehcache缓存配置中的缓存name一致
        this.cache = cacheManager.getCache("shiro-activeSessionCache");
    }
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest,
                                      ServletResponse servletResponse,
                                      Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        // 没有登录授权 且没有记住我
        if(!subject.isAuthenticated() && !subject.isRemembered()){
            // 如果没有登录，直接进行之后的流程
            //判断是不是Ajax请求，异步请求，直接响应返回未登录
            ResponseResultUtil responseResult = new ResponseResultUtil();

        }

        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}

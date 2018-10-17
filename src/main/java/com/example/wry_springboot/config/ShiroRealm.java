package com.example.wry_springboot.config;

import com.example.wry_springboot.index.db.entity.IndexEntity;
import com.example.wry_springboot.index.db.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    IndexService indexServiceImpl;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //授权
        log.debug("授予角色权限");
        //添加权限 和角色信息
        SimpleAuthorizationInfo authorizationInfo =new SimpleAuthorizationInfo();
        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        IndexEntity indexEntity = (IndexEntity) subject.getPrincipal();
        if(indexEntity.getUser_role().equals("root")){
            //超级管理员，添加所有角色，添加所有权限
            authorizationInfo.addRole("root");
            authorizationInfo.addStringPermission("7");
        }else {
            //普通用户,查询用户角色，根据角色权限查询
//            Integer userId = indexEntity.getUserId();
//            List<RoleEntity> roles = this.indexService.getUserRoles(userId);
//            if(null !=roles && roles.size()>0){
//                for(RoleEntity role : roles){
//                    authorizationInfo.addRole(role.getCode());
//                    //角色对应的权限数据
//                }
//            }
            authorizationInfo.addRole("ordinary");
            authorizationInfo.addRole("1");
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken)
            throws AuthenticationException {
        //UsernamePasswordToken用于存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String user_name =token.getUsername();
        Session sessionlocal = SecurityUtils.getSubject().getSession();
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();
        for(Session session1 : sessions){
            if (user_name.equals(String.valueOf(session1.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))){
                if(!sessionlocal.getId().equals(session1.getId())){
                    sessionManager.getSessionDAO().delete(session1);
                }
            }
        }
        // 调用数据层
        IndexEntity indexEntity = indexServiceImpl.getUserByName(user_name);
        log.info("用户登录认证：验证当前Subject时获取到token为：" + ReflectionToStringBuilder
                .toString(token, ToStringStyle.MULTI_LINE_STYLE));
        log.info(String.valueOf(indexEntity.getUser_pwd()));
           log.info("用户登录认证！用户信息user："+indexEntity);
           if (indexEntity == null ){
               throw new UnknownAccountException("用户不存在");
           }else {
               return new SimpleAuthenticationInfo(indexEntity, indexEntity.getUser_pwd(),getName());
               }
    }
    /**
     * 清除所有缓存
     */
    public void clearCachedAuth(){
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}

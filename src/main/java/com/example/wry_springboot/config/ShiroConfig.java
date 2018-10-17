package com.example.wry_springboot.config;
import com.sun.webkit.network.CookieManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
@Slf4j
@Configuration
public class ShiroConfig {
    /**
     * ShiroFilterFactoryBean 处理拦截资源文件过滤器
     *  </br>1,配置shiro安全管理器接口securityManage;
     *  </br>2,shiro 连接约束配置filterChainDefinitions;
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) throws Exception {
        //shiroFilterFactoryBean对象
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        // 配置shiro安全管理器 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //添加kickout认证
        HashMap<String,Filter> hashMap=new HashMap<String,Filter>();
        shiroFilterFactoryBean.setFilters(hashMap);
        // filterChainDefinitions拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 从上向下顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/file/**", "anon");
        filterChainDefinitionMap.put("/templates/*.html", "anon");

        // 配置退出过滤器,具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问【放行】-->
        filterChainDefinitionMap.put("/user/**", "authc");
        filterChainDefinitionMap.put("/hello/**", "authc");
        // 指定要求登录时的链接
        shiroFilterFactoryBean.setLoginUrl("/403");
//        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("user/getAllUserView");
        // 未授权时跳转的界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean
                .setFilterChainDefinitionMap(filterChainDefinitionMap);
        log.debug("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }

    /**
     * shiro安全管理器设置realm认证
     * @return
     */
    @Bean
    public org.apache.shiro.mgt.SecurityManager securityManager() throws Exception {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(shiroRealm());
        // //注入ehcache缓存管理器;
        securityManager.setCacheManager(ehCacheManager());
        // //注入session管理器;
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }
    /*******
     * 身份认证realm
     * 验证登录用户密码是否正确
     */
    @Bean
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm =new ShiroRealm();
        return shiroRealm;
    }
    /**
     * ehcache缓存管理器；shiro整合ehcache：
     * 通过安全管理器：securityManager
     * @return EhCacheManager
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        log.debug(
                "=====shiro整合ehcache缓存：ShiroConfiguration.getEhCacheManager()");
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:config/ehcache.xml");
        return cacheManager;
    }
    /**
     *
     * @描述：自定义cookie中session名称等配置
     * @创建时间：2018年5月8日 下午1:26:23
     * @returnResponseResult
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
        //DefaultSecurityManager
        SimpleCookie simpleCookie = new SimpleCookie();
        //如果在Cookie中设置了"HttpOnly"属性，那么通过程序(JS脚本、Applet等)将无法读取到Cookie信息，这样能有效的防止XSS攻击。
        simpleCookie.setHttpOnly(true);
        simpleCookie.setName("SHRIOSESSIONID");

        //单位秒
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }
    /**
     *
     * @描述：sessionManager添加session缓存操作DAO
     * @创建时间：2018年4月24日 下午8:13:52
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager() throws Exception{
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(ehCacheManager());
        //去掉URL中的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionDAO(enterCacheSessionDAO());
        sessionManager.setSessionIdCookie(sessionIdCookie());
        //相隔多久检查一次session的有效性
        sessionManager.setSessionValidationInterval(3600000);
        //session时长
        sessionManager.setGlobalSessionTimeout(3600000);
        return sessionManager;
    }

    /**
     * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 所以我们需要修改下doGetAuthenticationInfo中的代码,更改密码生成规则和校验的逻辑一致即可; ）
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(ehCacheManager());
        //new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1);// 散列的次数，比如散列两次，相当于 // md5(md5(""));
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }
    /**
     * EnterpriseCacheSessionDAO shiro sessionDao层的实现；
     * 提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     */
    @Bean
    public EnterpriseCacheSessionDAO enterCacheSessionDAO() {
        EnterpriseCacheSessionDAO enterCacheSessionDAO = new EnterpriseCacheSessionDAO();
        //添加缓存管理器
        enterCacheSessionDAO.setCacheManager(ehCacheManager());
        //添加ehcache活跃缓存名称（必须和ehcache缓存名称一致）
        enterCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        return enterCacheSessionDAO;
    }





}

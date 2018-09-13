package com.example.wry_springboot.config;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
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
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        //shiroFilterFactoryBean对象
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        // 配置shiro安全管理器 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //添加kickout认证
        HashMap<String,Filter> hashMap=new HashMap<String,Filter>();
        hashMap.put("kickout",kickoutSessionFilter());
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
        filterChainDefinitionMap.put("/user/**", "anon");
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
    public org.apache.shiro.mgt.SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(shiroRealm());
        // //注入ehcache缓存管理器;
        securityManager.setCacheManager(ehCacheManager());
        // //注入session管理器;
        securityManager.setSessionManager(sessionManager());
        //注入Cookie记住我管理器
        securityManager.setRememberMeManager(rememberMeManager());
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
     * 设置记住我cookie过期时间
     * @return
     */
    @Bean
    public SimpleCookie remeberMeCookie(){
        log.debug("记住我，设置cookie过期时间！");
        //cookie名称;对应前端的checkbox的name = rememberMe
        SimpleCookie scookie=new SimpleCookie("rememberMe");
        //记住我cookie生效时间30天 ,单位秒  [10天]
        scookie.setMaxAge(5*365*24*60*60);
        return scookie;
    }
    /**
     * 配置cookie记住我管理器
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        log.debug("配置cookie记住我管理器！");
        CookieRememberMeManager cookieRememberMeManager=new CookieRememberMeManager();
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
        cookieRememberMeManager.setCookie(remeberMeCookie());
        return cookieRememberMeManager;
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
    /**
     *
     * @描述：sessionManager添加session缓存操作DAO
     * @创建时间：2018年4月24日 下午8:13:52
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(ehCacheManager());
        //去掉URL中的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionDAO(enterCacheSessionDAO());
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        return sessionManager;
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
        simpleCookie.setMaxAge(5*365*24*60*60);
        return simpleCookie;
    }
    /**
     *
     * @描述：kickoutSessionFilter同一个用户多设备登录限制
     * @创建人：wyait
     * @创建时间：2018年4月24日 下午8:14:28
     * @return
     */
    public KickoutSessionFilter kickoutSessionFilter(){
        KickoutSessionFilter kickoutSessionFilter = new KickoutSessionFilter();
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        //这里我们还是用之前shiro使用的ehcache实现的cacheManager()缓存管理
        //也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
        kickoutSessionFilter.setCacheManager(ehCacheManager());
        //用于根据会话ID，获取会话进行踢出操作的；
        kickoutSessionFilter.setSessionManager(sessionManager());
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
        kickoutSessionFilter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
        kickoutSessionFilter.setMaxSession(1);
        //被踢出后重定向到的地址；
        kickoutSessionFilter.setKickoutUrl("/dologin");
        return kickoutSessionFilter;
    }

}

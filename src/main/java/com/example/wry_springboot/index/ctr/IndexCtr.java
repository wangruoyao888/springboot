package com.example.wry_springboot.index.ctr;

import com.example.wry_springboot.index.db.entity.IndexEntity;
import com.example.wry_springboot.index.db.service.IndexService;
import com.example.wry_springboot.user.db.entity.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/")
public class IndexCtr {
    @Autowired
    IndexService  indexServiceImpl;
    @RequestMapping(value = "login")
    public String login(){
        return "login";
    }
    @RequestMapping(value = "403")
    public String no(){
        return "403";
    }
    @RequestMapping(value = "test")
    @ResponseBody
    public Map<String, Object> val(){
        Map<String,Object> map1 =new HashMap<>();
        map1.put("code",0);
        Map<String,Object> map2 =new HashMap<>();
        map2.put("msg","");
        map1.put("data",map2);
        return map1;
    }
    @RequestMapping(value = "")
    public String tologin(){
        return "login";
    }
    @RequestMapping(value = "register")
    public String register(){
        return "register";
    }
    @RequestMapping(value = "dologin",method = RequestMethod.POST)
    @ResponseBody
    public String dologin(@RequestParam String user_name ,
                          @RequestParam String user_pwd,
                          @RequestParam (value = "check", required = false)boolean check){

            // 1、 封装用户名和密码到token令牌对象
            UsernamePasswordToken token = new UsernamePasswordToken(user_name,DigestUtils.md5Hex(user_pwd),true);
            Subject currentUser= SecurityUtils.getSubject();
            try{
                log.info("对用户进行登录验证..验证开始");
                currentUser.login(token);
                log.info("对用户进行登录验证..验证通过");
            }catch (UnknownAccountException e){
                //用户名不存在
                return "用户名不存在";
            }catch (IncorrectCredentialsException e){
                log.info("对用户进行登录验证..验证未通过,错误的凭证");
                return "密码错误";
            }catch(LockedAccountException lae){
                log.info("对用户进行登录验证..验证未通过,账户已锁定");
                return  "账户已锁定";
            }catch(ExcessiveAttemptsException eae){
                log.info("对用户进行登录验证..验证未通过,错误次数过多");
                return "用户名或密码错误次数过多";
            }
            if (currentUser.isAuthenticated()){
                if(currentUser.hasRole("root")){
                    return "root";
                }else if(currentUser.hasRole("ordinary")){
                    return "ordinary";
                }else {
                    return "no";
                }

            }else {
                return "登录失败";
            }

        }
    @RequestMapping(value = "doregister",method = RequestMethod.POST)
    @ResponseBody
    public String doregister(@RequestParam String user_name,
                             @RequestParam String user_pwd){
        IndexEntity indexEntity =new IndexEntity();
        indexEntity.setUser_name(user_name);
        indexEntity.setUser_pwd(DigestUtils.md5Hex(user_pwd));
        indexServiceImpl.doregister(indexEntity);
        return "200";

    }

}

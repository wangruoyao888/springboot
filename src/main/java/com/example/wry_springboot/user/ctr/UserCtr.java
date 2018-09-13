package com.example.wry_springboot.user.ctr;

import com.example.wry_springboot.Listener.isChange;
import com.example.wry_springboot.Util.exportUtil;
import com.example.wry_springboot.Util.imgUtil;
import com.example.wry_springboot.index.db.entity.IndexEntity;
import com.example.wry_springboot.user.db.entity.FileEntity;
import com.example.wry_springboot.user.db.entity.UserEntity;
import com.example.wry_springboot.user.db.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
@Controller
//@RestController
@RequestMapping("/user")
@Slf4j
public class UserCtr {
    @Autowired
    UserService userServiceImpl;
    @Value("${upLoadPath}")
    private String filePath;
    @RequestMapping(value = "/index")
    public String getAllUserf(){

            return "user/index" ;

    }
    @RequestMapping(value = "/userList")
    public String userList(){
        Subject currentUser= SecurityUtils.getSubject();
        if (currentUser.hasRole("root")){
            return "/user/userList";
        }else {
            return "403";
        }

    }
    @RequestMapping(value = "/uplodefile")
    public String uplodefile(){
        Subject currentUser= SecurityUtils.getSubject();
        if (currentUser.hasRole("root")){
            return "/user/upLodeFile";
        }else {
            return "403";
        }

    }
    @RequestMapping(value = "/fileList")
    public String fileList(){
        Subject currentUser= SecurityUtils.getSubject();
        if (currentUser.hasRole("root")){
            return "/user/fileList";
        }else {
            return "403";
        }

    }
    @RequestMapping(value = "/dowloadFile")
    public String dowloadFile(){
        Subject currentUser= SecurityUtils.getSubject();
        if (currentUser.hasRole("root")){
            return "/user/dowloadFile";
        }else {
            return "403";
        }

    }
    @RequestMapping(value = "/dataselect")
    public String dataselect(){
        Subject currentUser= SecurityUtils.getSubject();
        if (currentUser.hasRole("root")){
            return "/user/data_select";
        }else {
            return "403";
        }

    }
    @RequestMapping(value = "/chartline")
    public String chartline(){
        Subject currentUser= SecurityUtils.getSubject();
        if (currentUser.hasRole("root")){
            return "/user/chart_line";
        }else {
            return "403";
        }

    }
    @RequestMapping(value = "/chartbar")
    public String chartbar(){
        Subject currentUser= SecurityUtils.getSubject();
        if (currentUser.hasRole("root")){
            return "/user/chart_bar";
        }else {
            return "403";
        }

    }
    @RequestMapping(value = "/chartpie")
    public String chartpie(){
        Subject currentUser= SecurityUtils.getSubject();
        if (currentUser.hasRole("root")){
            return "/user/chart_pie";
        }else {
            return "403";
        }

    }
    @RequestMapping(value = "/chartradar")
    public String chartradar(){
        Subject currentUser= SecurityUtils.getSubject();
        if (currentUser.hasRole("root")){
            return "/user/chart_radar";
        }else {
            return "403";
        }

    }
    @RequestMapping(value = "/edittable")
    public String edittable(){
        Subject currentUser= SecurityUtils.getSubject();
        if (currentUser.hasRole("root")){
            return "/user/edittable";
        }else {
            return "403";
        }

    }
    @RequestMapping(value = "/loginverify")
    public String loginverify(){

            return "/user/loginverify";


    }
    @RequestMapping(value = "/actualTimeList")
    public String actualTimeList(){
        return "user/actualTimeList";
    }
    @RequestMapping(value = "/pageInfo")
    public String PageInfo(){
        return "user/pageInfo";
    }
    @RequestMapping(value = "/report")
    public String report(){
        return "user/reportList";

    }
    @RequestMapping(value = "/Echat")
    public String Echat(){
        return "user/Echat";

    }
    @RequestMapping(value = "/baiduMap")
    public String baiduMap(){
        return "user/baiduMap";

    }
    @RequestMapping(value = "/idcode")
    public String idcode(){
        return "user/idcode";

    }
    @RequestMapping(value = "/roletable")
    public String roletable(){
        return "user/roletable";

    }
    @RequestMapping(value = "/websocket")
    public String websocket(){
        return "user/websocket";

    }
    @RequestMapping(value = "/webSocketOneToOne")
    public String webSocketOneToOne(){
        return "user/webSocketOneToOne";

    }
    @RequestMapping(value = "/select")
    public String select(){
        return "user/select";
    }
    @RequestMapping(value = "/export" )
    @ResponseBody
    public String export(){
        List<String> listName =new ArrayList<>();
        listName.add("id");
        listName.add("姓名");
        listName.add("信息");
        listName.add("头像");
        listName.add("密码");
        List<String> listId =new ArrayList<>();
        listId.add("user_id");
        listId.add("user_name");
        listId.add("user_info");
        listId.add("user_img");
        listId.add("user_pwd");
        List<UserEntity> userList = userServiceImpl.getAllUser();
        exportUtil<UserEntity> exportUtil =new exportUtil<>();
        exportUtil.exportUtil("用户表",listName,listId,userList);

        return "200";
    }
    @RequestMapping(value = "/getPageInfo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPageInfo(@RequestParam Integer pagenum, @RequestParam Integer pagesize)  {
        PageHelper.startPage(pagenum,pagesize);
        Page<UserEntity> userEntityList = (Page<UserEntity>) userServiceImpl.getAllUser();
        Map<String,Object> map =new LinkedHashMap<>();
        map.put("filedata",userEntityList);
       return map;
    }
    @RequestMapping(value = "/getUserCount",method = RequestMethod.GET)
    @ResponseBody
    public Integer getUserCount(){
       return userServiceImpl.getUserCount();
    }
    @RequestMapping(value = "/getUserInfoNum",method = RequestMethod.POST)
    @ResponseBody
    public List<Integer> getUserInfoNum(){
      return userServiceImpl.getUserInfoNum();
    }
    @RequestMapping(value = "/insertline")
    @ResponseBody
    public void in(){
        userServiceImpl.in("休息",20);
        isChange isChange=new isChange();
        isChange.setIschange(true);
    }
    @RequestMapping(value = "/getLoginUser",method = RequestMethod.GET)
    @ResponseBody
    public IndexEntity getLoginUser(){
        Subject currentUser = SecurityUtils.getSubject();
        IndexEntity indexEntity =(IndexEntity) currentUser.getPrincipal();
        return indexEntity;
    }
    @RequestMapping(value = "/home")
    public String home(){
        return "hello/home" ;
    }
    @RequestMapping(value = "/getAllUser",method = RequestMethod.GET)
    @ResponseBody
    public List<UserEntity> getAllUser(){
        log.info("所有用户信息"+userServiceImpl.getAllUser());
        return userServiceImpl.getAllUser();
    }
    @RequestMapping(value = "/QueryGetAllUser",method = RequestMethod.GET)
    @ResponseBody
    public List<UserEntity> QueryGetAllUser(){

        return userServiceImpl.getAllUser();
    }
    @RequestMapping(value = "/editgetAllUser",method = RequestMethod.GET)
    @ResponseBody
    public String editgetAllUser() throws JsonProcessingException {
        Map<String,Object> map =new LinkedHashMap<>();
        map.put("code",0);
        map.put("msg","");
        map.put("data",userServiceImpl.getAllUser());
        ObjectMapper mapper =new ObjectMapper();
        return mapper.writeValueAsString(map);
    }
    @RequestMapping(value = "/getUserById",method = RequestMethod.POST)
    @ResponseBody
    public UserEntity getUserById(@RequestParam Integer id){
        return userServiceImpl.getUserById(id);
    }
    @Transactional
    @RequestMapping(value = "/delUser" , method = RequestMethod.POST)
    @ResponseBody
    public String delUsers (@RequestParam Integer id){
        userServiceImpl.delUser(id);
        return "200";
    }
    @Transactional
    @RequestMapping(value = "/updataUser",method = RequestMethod.POST)
    @ResponseBody
    public String updataUser(@RequestParam String user_name,@RequestParam String user_pwd,
                             @RequestParam String user_info,@RequestParam Integer user_id){
        UserEntity userEntity =new UserEntity();
        userEntity.setUser_id(user_id);
        userEntity.setUser_name(user_name);
        userEntity.setUser_pwd(user_pwd);
        userEntity.setUser_info(user_info);
        userServiceImpl.updataUser(userEntity);
        return "200";
    }
    @Transactional
    @RequestMapping(value = "/addUser" ,method = RequestMethod.POST)
    @ResponseBody
    public String addUser (@RequestParam String user_name,@RequestParam String user_pwd){
        System.out.printf(user_name);
        System.out.printf(user_pwd);
       UserEntity userEntity =new UserEntity();
       userEntity.setUser_name(user_name);
       userEntity.setUser_pwd(user_pwd);
       userServiceImpl.addUser(userEntity);
        return "200";
    }
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public List<UserEntity> search(@RequestParam String user_name){

        return  userServiceImpl.search(user_name);
    }
    @Transactional
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(MultipartFile file){

           if(file !=null){
               String fileOriginalName = file.getOriginalFilename();
               String file_name =null;
               String type =null;
               type = fileOriginalName.indexOf(".")!=-1?fileOriginalName.substring(fileOriginalName.lastIndexOf(".")+1,
                       fileOriginalName.length()):null;
               if(type!=null){
                   if("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())){
                       file_name = String.valueOf(System.currentTimeMillis())+fileOriginalName;
                      String file_path = filePath+file_name;
                       System.out.printf(file_path);
                       try {
                           file.transferTo(new File(file_path));
                           String file_src ="<a"+"  "+"href="+"\"/user/dowload/"+file_name+"\""+"  "+"class="+"\"layui-btn layui-btn-primary layui-btn-xs\""+">下载</a>";
                           String file_view ="<img"+"  "+"src="+"\"/file/"+file_name+"\""+"  "+"style="+"\"width: 80px;height: 80px\""+"></img>";
                           FileEntity fileEntity =new FileEntity();
                           fileEntity.setFile_name(file_name);
                           fileEntity.setFile_path(file_path);
                           fileEntity.setFile_view("/file/"+file_name);
                           fileEntity.setFile_src("/user/dowload/"+file_name);
                           userServiceImpl.uploadfileh(fileEntity);
                           userServiceImpl.uploadfile(file_name,file_path,file_src,file_view);
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       Map<String,Object> map =new LinkedHashMap<>();
                       Map<String,Object> map2 = new HashMap<>();
                       map2.put("src","<a"+"href="+"'"+"/user/dowload/"+file_name+"'"+"class="+"'"+"layui-btn"+"'"+">下载</a>");
                       map.put("code",0);
                       map.put("msg","");
                       map.put("data",map2);
                       return map;
                   }else {
                       return null;
                   }
               }else {
                   return null;
               }
           }else {
               return null;
           }

    }
    @RequestMapping(value = "/getfile",method = RequestMethod.GET)
    @ResponseBody
    public String getfiel() throws JsonProcessingException {
        log.info("所有用户信息"+userServiceImpl.getAllUser());
        List<FileEntity> list = userServiceImpl.getfile();
        Map<String,Object> map =new LinkedHashMap<>();
        map.put("code",0);
        map.put("msg","");
        map.put("count",100);
        map.put("data",list);
        ObjectMapper mapper =new ObjectMapper();
        return mapper.writeValueAsString(map);
    }
    @RequestMapping(value = "/getfile1",method = RequestMethod.GET)
    @ResponseBody
    public List<FileEntity> getfiel1(Model model)  {
        List<FileEntity> list = userServiceImpl.getfile1();
        model.addAttribute("list",list);
        return list;
    }
    @RequestMapping(value = "getsheng",method = RequestMethod.GET)
    @ResponseBody
    public String getsheng() throws JsonProcessingException {
        ObjectMapper mapper =new ObjectMapper();

        return  mapper.writeValueAsString(userServiceImpl.getsheng());
    }
    @RequestMapping(value = "getshi",method = RequestMethod.POST)
    @ResponseBody
    public String getshi(@RequestParam Integer sheng_id) throws JsonProcessingException {
        ObjectMapper mapper =new ObjectMapper();
        return mapper.writeValueAsString(userServiceImpl.getshi(sheng_id));
    }
    @RequestMapping(value = "/dowload/{filename}" )
    @ResponseBody
    public String dowload(
                          @PathVariable ("filename") String filename
    , HttpServletResponse response) throws IOException {
        String contentDisposition = "attachment;filename=1535614146911123.jpg";
        // 设置头
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",contentDisposition);
        // 输入流
        FileInputStream input = new FileInputStream("/springFile/img/1535614146911123.jpg");
        // 获取绑定了客户端的流
        ServletOutputStream output = response.getOutputStream();
        // 把输入流中的数据写入到输出流中
        IOUtils.copy(input,output);
        input.close();
        return "200";
    }
    @RequestMapping(value = "/check{data}",method={RequestMethod.GET})
    @ResponseBody
    public void check(HttpServletRequest req,HttpServletResponse response) {
        try {
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + "vcode.jpeg");
            String number = imgUtil.getNumber(4);
            imgUtil.getImage(response.getOutputStream(),number);
        } catch (Exception e) {

        }
    }


}

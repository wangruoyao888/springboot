package com.example.wry_springboot;
import com.example.wry_springboot.user.db.entity.UserEntity;
import com.example.wry_springboot.user.db.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class usertest {
    @Autowired
    private UserService userServiceImpl;
    @Value("${upLoadPath}")
    private String filePath;
    @Test
    public void pagefind(){
        PageHelper.startPage(3,5);
        Page<UserEntity> userEntityList = (Page<UserEntity>) userServiceImpl.getAllUser();

        log.info(""+userEntityList.getTotal());
        ObjectMapper mapper = new ObjectMapper();
        for (UserEntity user : userEntityList){
            try {
                log.info(mapper.writeValueAsString(user));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void getvalue() throws FileNotFoundException, UnsupportedEncodingException {
      File f =new File("/tmp");
        FileOutputStream fileOutputStream =new FileOutputStream(f);
        OutputStreamWriter writer =new OutputStreamWriter(fileOutputStream,"UTF-8");


    }
}

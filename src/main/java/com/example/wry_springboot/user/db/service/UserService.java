package com.example.wry_springboot.user.db.service;

import com.example.wry_springboot.user.db.entity.FileEntity;
import com.example.wry_springboot.user.db.entity.UserEntity;
import com.example.wry_springboot.user.db.entity.shengEntity;
import com.example.wry_springboot.user.db.entity.shiEntity;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<UserEntity> getAllUser();
    UserEntity getUserById(Integer id);
    void delUser(Integer id);
    void addUser(UserEntity User);
    void updataUser(UserEntity User);
    List<UserEntity> search(String user_name);
    Page<UserEntity> QueryGetAllUser(int pageNo , int PageSize);
    UserEntity getUserByName(String user_name);
    List<Integer> getUserInfoNum();
    void insertFile(String path);
    List<shengEntity> getsheng();
    List<shiEntity> getshi(Integer id);
    void uploadfile(String file_name,String file_path,String file_src ,String file_view);
    void uploadfileh(FileEntity fileEntity);
    List<FileEntity> getfile();
    List<FileEntity> getfile1();
    Integer getUserCount();
    void in (String name ,Integer value);
}

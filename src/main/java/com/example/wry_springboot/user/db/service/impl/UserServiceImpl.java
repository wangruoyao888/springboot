package com.example.wry_springboot.user.db.service.impl;

import com.example.wry_springboot.dao.UserDao;
import com.example.wry_springboot.user.db.entity.FileEntity;
import com.example.wry_springboot.user.db.entity.UserEntity;
import com.example.wry_springboot.user.db.entity.shengEntity;
import com.example.wry_springboot.user.db.entity.shiEntity;
import com.example.wry_springboot.user.db.service.UserService;
import com.github.pagehelper.Page;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    public List<UserEntity> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public UserEntity getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public void delUser(Integer id) {
           userDao.delUser(id);
    }

    @Override
    public void addUser(UserEntity User) {
            userDao.addUser(User);
    }

    @Override
    public void updataUser(UserEntity User) {
        userDao.updataUser(User);
    }

    @Override
    public List<UserEntity> search(String user_name) {

        return userDao.search(user_name);
    }

    @Override
    public Page<UserEntity> QueryGetAllUser(int pageNo, int PageSize) {

        return null;
    }

    @Override
    public UserEntity getUserByName(String user_name) {
        return userDao.getUserByName(user_name);
    }

    @Override
    public List<Integer> getUserInfoNum() {
        return userDao.getUserInfoNum();
    }

    @Override
    public void insertFile(String path) {
        userDao.insertFile(path);
    }

    @Override
    public List<shengEntity> getsheng() {
        return userDao.getsheng();
    }

    @Override
    public List<shiEntity> getshi(Integer id) {
        return userDao.getshi(id);
    }

    @Override
    public void uploadfile(String file_name, String file_path,String file_src,String file_view) {
        userDao.uploadfile(file_name,file_path,file_src,file_view);
    }

    @Override
    public void uploadfileh(FileEntity fileEntity) {
        userDao.uploadfileh(fileEntity);
    }

    @Override
    public List<FileEntity> getfile() {
        return userDao.getfile();
    }

    @Override
    public List<FileEntity> getfile1() {
        return userDao.getfile1();
    }

    @Override
    public Integer getUserCount() {
        return userDao.getUserCount();
    }

    @Override
    public void in(String name, Integer value) {
        userDao.in(name,value);
    }

}

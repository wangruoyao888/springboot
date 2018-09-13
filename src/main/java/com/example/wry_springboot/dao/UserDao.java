package com.example.wry_springboot.dao;

import com.example.wry_springboot.user.db.entity.FileEntity;
import com.example.wry_springboot.user.db.entity.UserEntity;
import com.example.wry_springboot.user.db.entity.shengEntity;
import com.example.wry_springboot.user.db.entity.shiEntity;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserDao {
    List<UserEntity> getAllUser();
    UserEntity getUserById(Integer id);
    void delUser(Integer id);
    void addUser(UserEntity User);
    void updataUser(UserEntity User);
    List<UserEntity> search(String user_name);
    Page<UserEntity> QueryGetAllUser();
    UserEntity getUserByName(String user_name);
    List<Integer> getUserInfoNum();
    void insertFile(String path);
    List<shengEntity> getsheng();
    List<shiEntity> getshi(Integer id);
    void uploadfile(@Param("file_name") String file_name,
                    @Param("file_path") String file_path,
                    @Param("file_src") String file_src,
                    @Param("file_view") String file_view);
    void uploadfileh(FileEntity fileEntity);
    List<FileEntity> getfile();
    List<FileEntity> getfile1();
    Integer getUserCount();
    void in (@Param("name")String name ,@Param("value")Integer value);
}

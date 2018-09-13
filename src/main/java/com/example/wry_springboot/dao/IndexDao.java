package com.example.wry_springboot.dao;

import com.example.wry_springboot.index.db.entity.IndexEntity;
import com.example.wry_springboot.index.db.entity.RoleEntity;
import com.example.wry_springboot.user.db.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface IndexDao {
    List<RoleEntity> getUserRoles(Integer id);
    IndexEntity getUserByName(String user_name);
    void  doregister(IndexEntity indexEntity);
}

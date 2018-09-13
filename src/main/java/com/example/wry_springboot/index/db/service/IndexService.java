package com.example.wry_springboot.index.db.service;

import com.example.wry_springboot.index.db.entity.IndexEntity;
import com.example.wry_springboot.index.db.entity.RoleEntity;
import com.example.wry_springboot.user.db.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
public interface IndexService {
    List<RoleEntity> getUserRoles(Integer id);
    IndexEntity getUserByName(String user_name);
    void  doregister(IndexEntity indexEntity);
}

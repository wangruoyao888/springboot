package com.example.wry_springboot.index.db.service.impl;

import com.example.wry_springboot.dao.IndexDao;
import com.example.wry_springboot.index.db.entity.IndexEntity;
import com.example.wry_springboot.index.db.entity.RoleEntity;
import com.example.wry_springboot.index.db.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    IndexDao indexDao;
    @Override
    public List<RoleEntity> getUserRoles(Integer id) {

        return indexDao.getUserRoles(id);
    }

    @Override
    public IndexEntity getUserByName(String user_name) {
        return indexDao.getUserByName(user_name);
    }

    @Override
    public void doregister(IndexEntity indexEntity) {
        indexDao.doregister(indexEntity);
    }
}

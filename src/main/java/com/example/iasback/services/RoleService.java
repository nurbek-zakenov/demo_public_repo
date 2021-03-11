package com.example.iasback.services;

import com.example.iasback.models.Role;
import com.example.iasback.repository.RoleMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;

    public Role getRoleById(Integer roleId){
        return roleMapper.getById(roleId);
    }
}

package com.example.iasback.repository;

import com.example.iasback.models.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RoleMapper {

    @Select("select * from roles where id= #{roleId}")
    Role getById(@Param("roleId") Integer roleId);
}

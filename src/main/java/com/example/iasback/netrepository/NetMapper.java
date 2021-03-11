package com.example.iasback.netrepository;


import org.apache.ibatis.annotations.Select;


public interface NetMapper {

    @Select("select 1")
    Integer getById();

}

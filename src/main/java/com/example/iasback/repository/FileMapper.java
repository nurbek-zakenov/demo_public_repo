package com.example.iasback.repository;


import com.example.iasback.models.Event;
import com.example.iasback.models.File;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FileMapper {




    @Select("insert into file ( original_name, extension, path, fs_name, event_id ) values ( #{original_name}, #{extension}, #{path}, #{fs_name}, #{event_id} ) " +
            "returning id, original_name, extension, path, fs_name, is_deleted, event_id")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public File addFile(File file);


    @Select("select * from file where event_id=#{id}")
    public List<File> getFiles (@Param("id")Integer id);
}

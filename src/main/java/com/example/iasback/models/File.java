package com.example.iasback.models;

import lombok.Data;


@Data
public class File {
    private Integer id;
    private Integer event_id;

    private String original_name;
    private String fs_name;
    private String extension;
    private String path;

    private boolean is_deleted;

}

package com.example.wry_springboot.user.db.entity;

public class FileEntity {
    private Integer pid;
    private String file_name;
    private String file_path;
    private Integer file_p_user;
    private String file_src;
    private String file_view;

    public String getFile_view() {
        return file_view;
    }

    public void setFile_view(String file_view) {
        this.file_view = file_view;
    }

    public String getFile_src() {
        return file_src;
    }

    public void setFile_src(String file_src) {
        this.file_src = file_src;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public Integer getFile_p_user() {
        return file_p_user;
    }

    public void setFile_p_user(Integer file_p_user) {
        this.file_p_user = file_p_user;
    }
}

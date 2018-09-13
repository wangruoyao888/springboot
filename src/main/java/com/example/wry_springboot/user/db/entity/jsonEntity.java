package com.example.wry_springboot.user.db.entity;

import java.util.List;

public class jsonEntity {
    /*返回码*/
    private Integer code;
    /*返回信息提示*/
    private String message;
    /*返回的数据*/
    private Integer count;


    private Object data;

    public jsonEntity(){}

    public jsonEntity(Integer code,String message,Object data,Integer count) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.count =count;
    }

    @Override
    public String toString() {

        return "[" + "code='" + code +", "+ "msg='" + message +" ,"+"count='"+ count +","+"data='"+data+"]";
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
}

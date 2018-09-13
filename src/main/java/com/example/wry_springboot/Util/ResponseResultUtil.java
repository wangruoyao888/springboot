package com.example.wry_springboot.Util;

import com.example.wry_springboot.Enum.ResultEnum;
import com.example.wry_springboot.user.db.entity.jsonEntity;

import javax.xml.transform.Result;
import java.io.Serializable;

public class ResponseResultUtil  {
    /**
     * 数据交互成功返回
     * @param object json返回的数据
     * */
    public static jsonEntity success(Object object){
        if(object==null){
            object = "";
        }
        return new jsonEntity(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(),object,10);
    }
    /**
     * 数据交互
     * */
    public static jsonEntity notFound(){
        return  new jsonEntity(ResultEnum.NOT_FOUND.getCode(),ResultEnum.NOT_FOUND.getMsg(),"",10);
    }
    /**
     * 参数异常
     * */
    public static jsonEntity parameterError(){
        return new jsonEntity(ResultEnum.PARAMETER_ERROR.getCode(),ResultEnum.PARAMETER_ERROR.getMsg(),"",10);
    }
    /**
     * 系统异常
     * */
    public static jsonEntity systemError(){
        return new jsonEntity(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(),"",10);
    }
}

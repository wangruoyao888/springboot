package com.example.wry_springboot.socket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
@ServerEndpoint("/webSocketOneToOne")
@Component
public class webSocketOneToOne {
    private static  Integer account =0;
    private static  Integer onlineCount=0;
    private static Map<String,webSocketOneToOne> users = new HashMap<>();
    private Session session;
    private String user_name;
    @OnOpen
    public void OnOpen(Session session) throws JsonProcessingException {
       this.session=session;
        String queryString = session.getQueryString();
        user_name = queryString.substring(queryString.indexOf("=")+1);
        users.put(user_name,this);
        onlineCount++;
        System.out.println("有新连接:"+user_name+"加入！当前在线人数为:"+onlineCount);
        ObjectMapper mapper =new ObjectMapper();
        System.out.println(mapper.writeValueAsString(users));

    }
    @OnMessage
    public void OnMessage(String msg ,Session session){
     for (webSocketOneToOne item : users.values()){
         if(item.user_name.equals("33") || item.user_name.equals("11")){
             item.session.getAsyncRemote().sendText(user_name+"说："+msg);
         }
     }

    }
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("错误"+error);
        error.printStackTrace();
    }
    @OnClose
    public void OnClose(){

    }

}

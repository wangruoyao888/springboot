package com.example.wry_springboot.socket;


import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
@ServerEndpoint("/websocket")
@Component
public class webSocket {
    private static int onlinCount =0;
    private static Set<webSocket> webSocketSet = new HashSet<webSocket>();
    private static List<String> names =new ArrayList<String>();
    private static Map<String,webSocket> users =new HashMap<>();
    private Session session;
    private String user_name;
    @OnOpen
    public void onOpen(Session session){
        this.session =session;
        webSocketSet.add(this);
        String queryString = session.getQueryString();
        user_name = queryString.substring(queryString.indexOf("=")+1);
        names.add(user_name);
        onlinCount++;
        System.out.println("有新连接:"+user_name+"加入！当前在线人数为:"+onlinCount);
        //广播
        for(webSocket item:webSocketSet){
            try {
                item.sendmsg("userList:"+user_name);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        onlinCount--;
        System.out.println("连接关闭！当前在线人数为"+onlinCount);
    }
    @OnMessage
    public void onMessage(String msg ,Session session){
        System.out.println("来自客户端"+user_name+"的消息"+msg);
        //广播
        for(webSocket item:webSocketSet){
            try {
                item.sendmsg(user_name+"说："+msg);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("错误"+error);
        error.printStackTrace();
    }

    private void sendmsg(String message) throws IOException {
        this.session.getAsyncRemote().sendText(message);
    }
}

package com.example.wry_springboot.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServiceEsay {
    public static void main(String arg[]) throws IOException {
        socketThread();
    }
    public static void socketesay()throws IOException{
        int port= 8081;
        ServerSocket serverSocket =new ServerSocket();
        Socket socket =serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder stringBuilder =new StringBuilder();
        while ((len = inputStream.read(bytes))!=-1){
            stringBuilder.append(new String(bytes,0,len,"UTF-8"));
        }
        System.out.println("get message from client: " + stringBuilder);
        inputStream.close();
        socket.close();
        serverSocket.close();
    }
    public static void socketThread()throws IOException{
        int port =8082;
        ServerSocket serverSocket =new ServerSocket();
        ExecutorService pool = Executors.newFixedThreadPool(100);
        while (true){
            Socket socket =serverSocket.accept();
            Runnable runnable=()->{
                try {
                    InputStream inputStream =socket.getInputStream();
                    byte [] bytes =new byte[1024];
                    int len;
                    StringBuffer stringBuffer =new StringBuffer();
                    while ((len =inputStream.read(bytes))!=-1){
                        stringBuffer.append(new String(bytes,0,len,"UTF-8"));
                    }
                    System.out.println("get message from client: " + stringBuffer);
                    inputStream.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            pool.submit(runnable);
        }
    }
}

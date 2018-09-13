package com.example.wry_springboot.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {
    private int port;
    private List<Socket> sockets;
    private ServerSocket serverSocket;
    public static void main(String[] args) {

         new SocketServer();

    }
    public SocketServer(){
       port = 8080;
       sockets =new ArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
            while (true){
                Socket socket =serverSocket.accept();
                sockets.add(socket);
                Mythread mythread = new Mythread(socket);
                mythread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    class Mythread extends Thread{
        Socket socketNew ;
        private BufferedReader bufferedReader;
        private PrintWriter printWriter;
        private String msg;
        public Mythread(Socket socket) {
            socketNew =socket;
        }
        public void run(){
            try {
                bufferedReader =new BufferedReader(new InputStreamReader(socketNew.getInputStream()));
                msg =socketNew.getInetAddress()+"进入链接";
                System.out.println(msg);
                sendMsg();
                while ((msg = bufferedReader.readLine())!=null){
                    msg = socketNew.getInetAddress()+"说："+msg;
                    System.out.println(msg);
                    sendMsg();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void sendMsg(){
            for(int i = sockets.size()-1;i>=0;i--){
                try {
                    printWriter =new PrintWriter(sockets.get(i).getOutputStream(),true);
                    printWriter.println(msg);
                    printWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

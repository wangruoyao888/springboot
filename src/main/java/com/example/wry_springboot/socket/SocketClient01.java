package com.example.wry_springboot.socket;

import org.omg.PortableServer.POA;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketClient01  {
    private int port = 8082;
    Socket socket =null;


    public static void main(String[] args) {
        new SocketClient01();
    }
    public SocketClient01(){
        try {
            socket =new Socket("127.0.0.1", port);
            new ClientThread().start();
            BufferedReader bufferedReader =new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            String msgClient;
            while ((msgClient = bufferedReader.readLine())!=null){
                System.out.println(msgClient);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class ClientThread extends Thread{
        public void run(){
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(System.in)
            );
            try {
                PrintWriter printWriter =new PrintWriter(socket.getOutputStream(),true);
                String msg ;
                while (true){
                    msg =bufferedReader.readLine();
                    printWriter.println(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

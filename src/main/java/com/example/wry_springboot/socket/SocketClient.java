package com.example.wry_springboot.socket;
import sun.nio.ch.ChannelInputStream;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient  {
    public static void main(String[] args) throws IOException {
        SocketClient();

    }
    public static void  SocketClient() throws IOException {
        int port = 8082;
        Socket socket =null;
        socket =new Socket("127.0.1.1", port);
            while (true){
                socket =new Socket("127.0.1.1", port);
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                Scanner scanner =new Scanner(System.in);
                String str1 = scanner.nextLine();
                printWriter.println(str1);
                printWriter.close();
                socket.close();
            }

    }

}

package com.example.wry_springboot.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        ServerSocket serverSocket =new ServerSocket(port);
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

    /*******
     * corePoolSize - 池中所保存的线程数，包括空闲线程。
     * maximumPoolSize-池中允许的最大线程数。
     * keepAliveTime - 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
     * unit - keepAliveTime 参数的时间单位。
     * workQueue - 执行前用于保持任务的队列。此队列仅保持由 execute方法提交的 Runnable任务。
     * threadFactory - 执行程序创建新线程时使用的工厂。
     * handler - 由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序。
     */
    public static void socketThread()throws IOException{
        int port =8082;
        ServerSocket serverSocket =new ServerSocket(port);
        /****
         *  定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
         *  定长线程池的corePoolSize、maximumPoolSize相同。都是设定值。
         */
        //ExecutorService pool = Executors.newFixedThreadPool(100);
        /***
         * 缓存线程池，如果线程池长度超过处理需要，可回收空闲线程，若无可回收，则新建线程。
         * 缓存线程池corePoolSize为0，maximumPoolSize则是int最大值。
         */
        //ExecutorService pool =Executors.newCachedThreadPool();
        /***
         * 计划线程池，支持定时及周期性任务执行。
         * 单线程线程池corePoolSize和maximumPoolSize都是1。
         */
        //ExecutorService pool = Executors.newScheduledThreadPool(100);
        /***
         *单线程线程池，用唯一的线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
         */
        //ExecutorService pool = Executors.newSingleThreadExecutor();
        ExecutorService pool =Executors.newCachedThreadPool();
        while (true){
            Socket socket =serverSocket.accept();

            Runnable runnable=()->{
                try {
                    String line;
                    DataInputStream inputStream =new DataInputStream(socket.getInputStream());
                    StringBuffer stringBuffer=new StringBuffer();
                    while ((line=inputStream.readLine())!=null){
                        stringBuffer.append(line);
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

package com.example.wry_springboot.Thread;

public class ExtendsThread extends Thread{
    private Thread thread;
    private String threadName;
    ExtendsThread(String name){
        threadName =name;
        System.out.println("Creating " +  threadName );
    }
    public void run() {
        for(int i =4 ; i>0; i--){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }
    public void start(){
        if(thread==null){
            thread =new Thread(this,threadName);
            thread.start();
        }
    }
    public static void main(String arg[]){
        ExtendsThread e1 =new ExtendsThread("thread_1");
        e1.start();
        ExtendsThread e2 =new ExtendsThread("thread_2");
        e2.start();
    }
}

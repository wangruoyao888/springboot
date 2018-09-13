package com.example.wry_springboot.Thread;

public class RunnableThread implements Runnable{
    private Thread thread;
    private String threadName;
    RunnableThread(String name){
        threadName = name;
        System.out.println("Creating " +  threadName );
    }
    @Override
    public void run() {
        for(int i =4;i>0;i--){
            System.out.println("Thread: " + threadName + ", " + i);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("Thread " +  threadName + " interrupted.");
            }

        }
        System.out.println("Thread " +  threadName + " exiting.");
    }
    public void start(){
        System.out.println("Starting " +  threadName );
        if(thread == null){
            thread =new Thread(this,threadName);
            thread.start();
        }
    }
    public static void main(String arg[]){
        RunnableThread r1 = new RunnableThread("thread_1");
        r1.start();
        RunnableThread r2 = new RunnableThread("thread_2");
        r2.start();
    }
}

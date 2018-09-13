package com.example.wry_springboot.Thread;



public class ChouJiang  {
    public static void main(String[] args) {
        Clerk01 clerk01 = new Clerk01();
        Thread pro = new Thread(new Productor01(clerk01));
        Thread con = new Thread(new Consumer01(clerk01));
        pro.start();
        con.start();


    }
    //售货员
    static class Clerk01{
        private int product =0;
        //生产商品
        public synchronized void add(){
            if(product >= 20){
                try {
                    wait();
                    System.out.println("商品满，停止生产");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }else {
                product++;
                System.out.println("生产了1件商品，商品总数为："+product);
                notifyAll();
            }
        }
        //消费商品
        public synchronized void remove(){
            if(this.product <=0){
                try {
                    wait();
                    System.out.println("暂时没有商品");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }else {
                product--;
                System.out.println("顾客取走一件商品,商品总数为："+product);
                notifyAll();
            }
        }
    }
    //生产者
    static class Productor01 implements Runnable{
        Clerk01 clerk01;
         public Productor01(Clerk01 clerk01){
              this.clerk01=clerk01;
         }
        @Override
        public void run() {
            System.out.println("现在开始生产产品");
            while (true){
                try {
                    Thread.sleep((int)(Math.random()*2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clerk01.add();
            }

        }
    }
    //消费者
    static class Consumer01 implements Runnable{
        Clerk01 clerk01;
        public Consumer01(Clerk01 clerk01){
            this.clerk01 = clerk01;
        }
        @Override
        public void run() {
            System.out.println("消费者开始取产品");
            while (true){
                try {
                    Thread.sleep((int)(Math.random()*2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clerk01.remove();
            }

        }
    }

}

package com.example.wry_springboot.Linked;

import org.junit.Test;

import javax.xml.soap.Node;

public class SingleLinked {
    //链表节点的个数
    private int size;
    //头节点
    private Node head;
    public SingleLinked(){
       size=0;
        head =null;
    }
    //链表的每个节点类
    private class Node{
        //每个节点的数据
        private Object data;
        //每个节点指向下一个节点的链接
        private Node next;
        public Node(Object data){
            this.data = data;
        }
    }
    //在链表头添加元素
    public Object addHead(Object object){
        Node newHead =new Node(object);
        if (size == 0){
            head =newHead;
        }else {
            newHead.next = head;
            head = newHead;
        }
        size++;
        return object;
    }
    //在链表头删除元素
    public Object deleteHead(){
        Object obj =head.data;
        head =head.next;
        size--;
        return obj;
    }
    //查找指定元素，找到了返回节点Node，找不到返回null
    public Node find (Object obj){
        Node current =head;
        int tempSize = size;
        while (tempSize>0){
            if(obj.equals(current.data)){
                return current;
            }else {
                current=current.next;
            }
            tempSize--;
        }
        return null;
    }
   // 删除指定的元素，删除成功返回true
    public boolean delete(Object value){
        if(size == 0){
            return false;
        }
        Node current =head;
        Node previous =head;
        while (current.data != value){
            if(current.next == null){
                return false;
            }else {
                previous = current;
                current =current.next;
            }
        }
        //如果删除的节点是第一个节点
        if (current == head){
            head = current.next;
            size--;
        }
        return false;
    }
    //判断链表是否为空
    public boolean isEmpty(){
        return (size == 0);
    }
    //显示节点信息
    public void display(){
        if(size>0){
            Node node =head;
            int tempSize =size;
            if(tempSize ==1){
                //当前链表只有一个节点
                System.out.println("["+node.data+"]");
                return;
            }
            while (tempSize>0){
                if(node.equals(head)){
                    System.out.print("["+node.data+"->");
                }else if(node.next == null){
                    System.out.print(node.data+"]");
                }else {
                    System.out.print(node.data+"->");
                }
                node =node.next;
                tempSize--;
            }
            System.out.println();
        }else {
            System.out.println("[]");
        }
    }
    @Test
    public void test(){
        SingleLinked singleLinked =new SingleLinked();
        singleLinked.addHead("A");
        singleLinked.addHead("B");
        singleLinked.addHead("C");
        singleLinked.addHead("D");
        singleLinked.display();
    }
}

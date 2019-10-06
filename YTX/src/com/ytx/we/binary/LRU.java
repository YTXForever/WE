package com.ytx.we.binary;

import java.util.HashMap;
import java.util.Map;

public class LRU {
    private Map<String,Node<String,Integer>> map = new HashMap<>();
    private Node<String,Integer> head;
    private Node<String,Integer> tail;
    private int capacity =10;
    private int count = 0;
    public LRU(int capacity){
        head = new Node<>();
        tail = new Node<>();
        head.setNext(tail);
        head.setPre(null);
        tail.setPre(head);
        tail.setNext(null);
        this.capacity = capacity;
    }
    public void put(String key,int value){
        Node<String,Integer> old = map.get(key);
        if(old == null){
            Node<String,Integer> node = new Node<>();
            node.setKey(key);
            node.setValue(value);
            //put之前没有该数据
            if(count >= capacity){
                map.remove(tail.getPre().getKey());
                removeNode(tail.getPre());
                count--;

            }
            node.setNext(head.getNext());
            head.getNext().setPre(node);
            node.setPre(head);
            head.setNext(node);
            count++;
            map.put(key,node);

        }else{
            old.setValue(value);
            old.getPre().setNext(old.getNext());
            old.getNext().setPre(old.getPre());
            old.setNext(head.getNext());
            head.getNext().setPre(old);
            head.setNext(old);
            old.setPre(head);
        }

    }
    public int get(String key){
        Node<String,Integer> node = map.get(key);
        if(node == null){
            return -1;
        }
        node.getPre().setNext(node.getNext());
        node.getNext().setPre(node.getPre());
        node.setNext(head.getNext());
        head.getNext().setPre(node);
        head.setNext(node);
        node.setPre(head);
        return node.getValue();
    }

    public void remove(String key){
        Node<String,Integer> node = map.get(key);
        if(node == null){
            return;
        }
        removeNode(node);
        map.remove(node.getKey());
        count--;
    }
    private void removeNode(Node<String,Integer> node){
        node.getPre().setNext(node.getNext());
        node.getNext().setPre(node.getPre());
        node.setNext(null);
        node.setPre(null);
    }

    public static void main(String[] args) {

        LRU cache = new LRU(5);
        cache.put("k1",1);
        cache.put("k2",2);
        cache.put("k3",3);

        cache.put("k4",4);
        cache.put("k5",5);
        cache.put("k1",11);
        cache.get("k4");

        cache.put("k6",6);
        cache.put("k7",7);

        for(String key:cache.map.keySet()){
            System.out.print("【"+key+"】");
        }
    }
}

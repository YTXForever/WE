package com.ytx.we.binary;

import java.util.*;

public class SortChar {
    public static void main(String[] args) {
    List<String> list = new ArrayList<>();
    list.add("aa");
    list.add("ac");
    list.add("ab");
    list.add("aa");
    list.add("aa");
    list.add("ab");

    //期待输出 aa：3 ab：2 ac：1
    Map<String,Integer> treeMap = new TreeMap<>();
    treeMap.put("a",1);
    treeMap.put("b",2);
    treeMap.put("c",3);
    treeMap.put("e",5);
    List<Map.Entry<String,Integer>> l = new ArrayList<>(treeMap.entrySet());
    Collections.sort(l,new Comparator<Map.Entry<String, Integer>>(){
        @Override
        public int compare(Map.Entry<String, Integer> o1,Map.Entry<String, Integer> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
    });
    treeMap.put("d",4);
    for(Map.Entry<String,Integer> entry:l){
        System.out.println(entry.getKey()+":"+entry.getValue());
    }

 }

}

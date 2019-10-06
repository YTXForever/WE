package com.ytx.we.binary;

import java.util.HashMap;
import java.util.Map;

public class StringSort {
    //key 是字符串
    Map<String,Integer> map = new HashMap<>();
    String[] keyArr = new String[16];
    Integer[] countArr = new Integer[16];
    int nextIndex = 0;
    public void put(String key){
        Integer index = map.get(key);
        if(index == null){
            String oldKey = keyArr[nextIndex];
            keyArr[nextIndex] = key;
            countArr[nextIndex] = 1;
            map.put(key,nextIndex);
            if(nextIndex < keyArr.length-1){
                nextIndex++;
            }else {
                map.remove(oldKey);
            }
        }else{
            countArr[index] = countArr[index]+1;
            int tmpIndex = index;
            while (tmpIndex>0&& countArr[tmpIndex]> countArr[tmpIndex-1]) {
                //顺序需要改变
                map.put(keyArr[tmpIndex],map.get(keyArr[tmpIndex])-1);
                map.put(keyArr[tmpIndex-1],map.get(keyArr[tmpIndex-1])+1);
                swap(countArr,tmpIndex,tmpIndex-1);
                swap(keyArr,tmpIndex,tmpIndex-1);
                tmpIndex--;
            }


        }
    }

    private void swap(Object[] arr,int index1,int index2){
        Object tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }

    public String[] getKeyArr() {
        return keyArr;
    }

    public Integer[] getCountArr() {
        return countArr;
    }

    public int getNextIndex() {
        return nextIndex;
    }

    public static void main(String[] args) {
        StringSort ss = new StringSort();
        ss.put("a");
        ss.put("b");
        ss.put("b");
        ss.put("a");
        ss.put("c");
        ss.put("d");
        ss.put("f");
        ss.put("e");
        ss.put("h");
        ss.put("j");
        ss.put("k");
        ss.put("i");
        ss.put("k");
        ss.put("w");
        ss.put("i");
        ss.put("am");
        ss.put("ytx");
        ss.put("ytx");
        ss.put("hello");
        ss.put("niyao");
        ss.put("hi");
        ss.put("happy");
        ss.put("happy");
        ss.put("wyd");
        ss.put("are you sb?");
        ss.put("a");
        String[] keys = ss.getKeyArr();
        Integer[] counts =ss.getCountArr();
        System.out.println("count="+(ss.getNextIndex()+1));

        for(int i=0;i<=ss.getNextIndex();i++){
            System.out.println(keys[i]+":"+counts[i]);
        }
    }
}

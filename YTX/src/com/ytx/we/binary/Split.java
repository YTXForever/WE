package com.ytx.we.binary;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Split {
    public static void main(String[] args) {
       String[] regexs = { "--", "ghi", ":", "-", "rst" };
        List<String> result = split("abc---def::ghi::jkl:mno-",regexs);
        for(String s :result){
            System.out.print("【"+s+"】");
        }

    }
    private static List<String> split(String source,String[] regexs){
        List<String> result = new ArrayList<>();
        if(regexs != null && regexs.length!=0){
            result.add(source);
            for(String regex : regexs){
                Iterator<String> iterator = result.iterator();
                List<String> arrList = new ArrayList<>();
                while (iterator.hasNext()){
                    String s = iterator.next();
                    String[] arr = s.split(regex);
                    Collections.addAll(arrList,arr);
                    iterator.remove();
                }
                result.addAll(arrList);

            }
        }
        return result;

    }
}

package com.ytx.we.jvm;

public class ByteCodeSample {
    public static void main(String[] args) {
        int i = -2;
        int aa=32766;
        int j = 6;
        String s = new String("w d l j");
        ++i;
        j++;
        aa++;
        System.out.print(i);
        System.out.println(j);
        System.out.println(aa);
        System.out.println(s);

    }
}

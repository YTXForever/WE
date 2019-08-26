package com.ytx.we.stream;


import java.io.*;

public class StreamTest {
    public static void main(String[] args) throws IOException {
        String path = "/Users/wangyadong/WE/YTX/src/com/ytx/we/stream/Hello.txt";
        //inputStream
        InputStream is = new FileInputStream(path);
        byte[] b = new byte[1024];
        int len1=0;
        while((len1=is.read(b))!=-1){
            System.out.print(new String(b,0,len1));
        }
        is.close();

        //reader
        Reader r = new FileReader(path);
        BufferedReader br = new BufferedReader(r);
        String readLine = "";
        while ((readLine = br.readLine()) !=null && readLine.length()>0){
            System.out.println(readLine);
        }
        br.close();
        r.close();


        String outPath = "/Users/wangyadong/WE/YTX/src/com/ytx/we/stream/out.txt";
        //outputstream
        //File f = new File(outPath);
        OutputStream os = new FileOutputStream(outPath);
        BufferedOutputStream bo = new BufferedOutputStream(os);
        bo.write("哈哈哈～～\n我的天呐\n原来是这样".getBytes());
        bo.close();
        os.close();

        //writer
        /*Writer w = new FileWriter(outPath);
        BufferedWriter bw = new BufferedWriter(w);
        bw.write("哎呦喂～～\n\n那就这样吧");
        bw.close();
        w.close();*/
    }
}

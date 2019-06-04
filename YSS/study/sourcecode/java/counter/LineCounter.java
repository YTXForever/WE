package counter;

import java.io.*;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuh
 * @date 2019-06-04 11:05
 **/
public class LineCounter {


    public static int countLine(String path, String suffix) throws IOException {
        AtomicInteger atomicInteger = new AtomicInteger();
        _countLine(new File(path),suffix,atomicInteger);
        return atomicInteger.get();
    }

    private static void _countLine(File file, String suffix, AtomicInteger atomicInteger) throws IOException {
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(files == null || files.length==0){
                return;
            }
            for (File f : files) {
                _countLine(f,suffix,atomicInteger);
            }
        }else if(file.isFile() && file.getName().endsWith(suffix)){
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
            lineNumberReader.skip(Integer.MAX_VALUE);
            atomicInteger.getAndAdd(lineNumberReader.getLineNumber());
            lineNumberReader.close();
        }
    }


    public static int countLine1(String path,String suffix) throws IOException {
        int line = 0;
        LinkedList<File> files = new LinkedList<>();
        files.addLast(new File(path));
        while(!files.isEmpty()){
            File file = files.removeFirst();
            if(file.isDirectory()){
                File[] fileArr = file.listFiles();
                if(fileArr == null || fileArr.length==0){
                    continue;
                }
                for (File f : fileArr) {
                    files.addLast(f);
                }
            }else if(file.isFile() && file.getName().endsWith(suffix)){
                LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
                lineNumberReader.skip(Integer.MAX_VALUE);
                line += lineNumberReader.getLineNumber();
                lineNumberReader.close();
            }
        }
        return line;
    }

    public static void main(String[] args) throws IOException {
        long l = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            int line = countLine1("/Users/yuh/develop/myprojects/sevendays/src/main/java", ".java");
        }
        System.out.println(System.currentTimeMillis()-l);
//        int line1 = countLine1("/Users/yuh/develop/myprojects/sevendays/src/main/java", ".java");
//        System.out.println(line1);
    }
}

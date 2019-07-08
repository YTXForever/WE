package bitmap;

/**
 * @author yuh
 * @date 2019-07-05 07:56
 **/
public class MyBitMap {

    private long[] bytes;
    private int nBits;

    public MyBitMap(int nBits) {
        this.nBits = nBits;
        bytes = new long[nBits / 64 + 1];
    }

    public void set(int set) {
        int index = set / 64;
        int bitIndex = set % 64;
        bytes[index] |= (1 << bitIndex - 1);
    }

    public boolean get(int get) {
        int index = get / 64;
        int bitIndex = get % 64;
        return (bytes[index] >> (bitIndex - 1) & 1) == 1;
    }


    public static void main(String[] args) {
        MyBitMap myBitMap = new MyBitMap(100);
        myBitMap.set(1);
        myBitMap.set(2);
        myBitMap.set(3);
        System.out.println(myBitMap.get(1));
        System.out.println(myBitMap.get(2));
        System.out.println(myBitMap.get(3));
    }
}

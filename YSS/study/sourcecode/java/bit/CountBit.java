package bit;

import java.util.Arrays;

/**
 * @author yuh
 * @date 2019-07-03 08:07
 **/
public class CountBit {

    public static boolean isPowerOfTwo(int num) {
        return (num & num - 1) == 0;
    }


    public static int countBit(int num) {
        int index = 0;
        while (num != 0) {
            num &= num - 1;
            index++;
        }
        return index;
    }


    public static int[] countBitRange(int num) {
        int[] arr = new int[num + 1];
        arr[0] = 0;
        for (int i = 1; i <= num; i++) {
            arr[i] = arr[i & i - 1] + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(i + " => " + isPowerOfTwo(i) + " => " + countBit(i));
        }
        System.out.println(Arrays.toString(countBitRange(9)));
    }


}

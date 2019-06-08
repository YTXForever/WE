package dp;

import java.util.LinkedList;
import java.util.List;

/**
 * 动态规划解决背包问题
 * 用状态表转移法 每一行基于上一行扩展
 * 重点就是把限制的范围定在一个区间之内
 * 最后一行你达到想要的结果
 *
 * @author yuh
 * @date 2019-06-08 07:16
 **/
public class Bag {

    public static int maxWeight(int[] item, int w) {
        int max = w + 1;
        boolean[][] table = new boolean[item.length][w + 1];
        table[0][0] = true;
        if (item[0] <= w) {
            table[0][item[0]] = true;
        }

        for (int i = 1; i < item.length; i++) {
            //copy from prev line
            for (int j = 0; j <= w; j++) {
                if (table[i - 1][j]) {
                    table[i][j] = true;
                }
            }
            for (int j = 0; j <= w - item[i]; j++) {
                if (table[i - 1][j]) {
                    table[i][j + item[i]] = true;
                }
            }
        }

        for (int i = w; i >= 0; i--) {
            if (table[item.length - 1][i]) {
                return i;
            }
        }
        return -1;
    }

    public static int maxValue(int[] item, int[] value, int w) {
        int max = w + 1;
        int[][] table = new int[item.length][w + 1];
        for (int i = 0; i < item.length; i++) {
            for (int j = 0; j < w + 1; j++) {
                table[i][j] = -1;
            }
        }
        table[0][0] = 0;
        if (item[0] <= w) {
            table[0][item[0]] = value[0];
        }

        for (int i = 1; i < item.length; i++) {
            //copy from prev line
            for (int j = 0; j <= w; j++) {
                if (table[i - 1][j] >= 0) {
                    table[i][j] = table[i - 1][j];
                }
            }
            for (int j = 0; j <= w - item[i]; j++) {
                if (table[i][j] >= 0
                        && table[i - 1][j] + value[i] > table[i][j + item[i]]) {
                    table[i][j + item[i]] = table[i - 1][j] + value[i];
                }
            }
        }
        int maxValue = 0;
        for (int i = w; i >= 0; i--) {
            if (table[item.length - 1][i] >= maxValue) {
                maxValue = table[item.length - 1][i];
            }
        }
        return maxValue;
    }

    public static List<Integer> double11(int[] item, int w) {
        int max = 3 * w;
        boolean[][] table = new boolean[item.length][max + 1];
        table[0][0] = true;
        if (item[0] <= max) {
            table[0][item[0]] = true;
        }

        for (int i = 1; i < item.length; i++) {
            //copy from prev line
            for (int j = 0; j <= max; j++) {
                if (table[i - 1][j]) {
                    table[i][j] = true;
                }
            }
            for (int j = 0; j <= max - item[i]; j++) {
                if (table[i - 1][j]) {
                    table[i][j + item[i]] = true;
                }
            }
        }

        int i;
        for (i = w; i <= max; i++) {
            if (table[item.length - 1][i]) {
                break;
            }
        }
        if (i == max + 1) {
            return null;
        }
        LinkedList<Integer> list = new LinkedList<>();
        for (int j = item.length - 1; j > 0; j--) {
            if (i - item[j] >= 0 && table[j - 1][i - item[j]]) {
                list.addFirst(j);
                i -= item[j];
            }
        }
        if (i > 0) {
            list.addFirst(0);
        }
        return list;
    }


    public static void main(String[] args) {
        int[] arr = {1, 1, 5};

        List<Integer> list = double11(arr, 2);
        System.out.println(list);


//        int[] value = {2, 100, 5};
//        int maxWeight = maxValue(arr, value, 5);
//        System.out.println(maxWeight);
    }
}

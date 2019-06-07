package recall;

import java.util.Arrays;

/**
 * 八皇后问题
 *
 * @author yuh
 * @date 2019-06-07 08:45
 **/
public class Queue {

    public int[] res = new int[8];
    private boolean hasFind = false;

    public void findQueue() {
        _findQueue(0);
    }

    private void _findQueue(int i) {
        if (i == 8) {
            hasFind = true;
            printQueue();
            return;
        }
        for (int j = 0; j < 8; j++) {
            if (hasFind) {
                return;
            }
            if (isOK(i, j)) {
                res[i] = j;
                _findQueue(i + 1);
            }
        }
    }

    public boolean isOK(int row, int col) {
        int leftUp = col - 1, rightUp = col + 1;
        row--;
        while (row >= 0) {
            if (res[row] == col) {
                return false;
            }
            if (leftUp >= 0 && res[row] == leftUp) {
                return false;
            }
            if (rightUp < 8 && res[row] == rightUp) {
                return false;
            }
            row--;
            leftUp--;
            rightUp++;
        }

        return true;
    }


    public void printQueue() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (res[i] == j) {
                    System.out.print(" Q ");
                } else {
                    System.out.print(" * ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new Queue().findQueue();
    }


}

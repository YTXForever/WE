package dp;

/**
 * 走法
 *
 * @author yuh
 * @date 2019-07-04 07:45
 **/
public class Path1 {

    public static int paths(boolean[][] point) {
        int[][] res = new int[point.length][point[0].length];
        boolean pass = true;
        for (int i = res.length - 1; i >= 0; i--) {
            if (!point[i][res[0].length - 1]) {
                pass = false;
            }
            if (!pass) {
                res[i][res[0].length - 1] = 0;
            } else {
                res[i][res[0].length - 1] = 1;
            }
        }


        for (int i = res[0].length - 1; i >= 0; i--) {
            if (!point[res.length - 1][i]) {
                pass = false;
            }
            if (!pass) {
                res[res.length - 1][i] = 0;
            } else {
                res[res.length - 1][i] = 1;
            }
        }

        for (int i = res.length - 2; i >= 0; i--) {
            for (int j = res[0].length - 2; j >= 0; j--) {
                if (!point[i][j]) {
                    res[i][j] = 0;
                } else {
                    res[i][j] = res[i + 1][j] + res[i][j + 1];
                }
            }
        }
        return res[0][0];
    }

    public static void main(String[] args) {
        boolean[][] point = new boolean[2][2];
        for (int i = 0; i < point.length; i++) {
            for (int j = 0; j < point[0].length; j++) {
                point[i][j] = true;
            }
        }
        point[0][1] = false;
        System.out.println(paths(point));
    }
}

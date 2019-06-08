package dp;

/**
 * 最短路线问题
 * 对角转移法
 * (i-i,j) / (i,j-1) / (i-1,j-1) -> (i,j)
 *
 * @author yuh
 * @date 2019-06-08 08:06
 **/
public class Path {


    public static int path(int[][] matrix) {
        int[][] table = new int[matrix.length][matrix[0].length];
        int sum = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            sum += matrix[0][i];
            table[0][i] = sum;
        }
        sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            sum += matrix[i][0];
            table[i][0] = sum;
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                table[i][j] = Math.min(table[i - 1][j], table[i][j - 1]) + matrix[i][j];
            }
        }
        return table[matrix.length - 1][matrix[0].length - 1];
    }

    public static void main(String[] args) {
        int[][] arr = {{1, 2, 3}, {1, 2, 3}};
        System.out.println(path(arr));
    }
}

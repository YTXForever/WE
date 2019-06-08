package dp;

/**
 * 编辑距离和最长公共子串
 *
 * @author yuh
 * @date 2019-06-08 08:27
 **/
public class DpStr {


    public static int edit(String str1, String str2) {
        int[][] table = new int[str1.length()][str2.length()];
        //初始化table第一行
        for (int i = 0; i < str2.length(); i++) {
            if (str1.charAt(0) == str2.charAt(i)) {
                table[0][i] = i;
            } else if (i != 0) {
                table[0][i] = table[0][i] + 1;
            } else {
                table[0][i] = 1;
            }
        }
        //初始化table第一列
        for (int i = 0; i < str1.length(); i++) {
            if (str2.charAt(0) == str1.charAt(i)) {
                table[i][0] = i;
            } else if (i != 0) {
                table[i][0] = table[i - 1][0] + 1;
            } else {
                table[i][0] = 1;
            }
        }
        //填表
        for (int i = 1; i < str1.length(); i++) {
            for (int j = 1; j < str2.length(); j++) {
                if (str1.charAt(i) == str2.charAt(j)) {
                    table[i][j] = min(table[i - 1][j] + 1, table[i][j - 1] + 1, table[i - 1][j - 1]);
                } else {
                    table[i][j] = min(table[i - 1][j] + 1, table[i][j - 1] + 1, table[i - 1][j - 1] + 1);
                }
            }
        }
        return table[str1.length() - 1][str2.length() - 1];
    }

    private static int min(int a, int b, int c) {
        if (a < b) {
            return a < c ? a : c;
        }
        if (a > b) {
            return b > c ? b : c;
        } else {
            if (a < c) {
                return a;
            }
            return c;
        }
    }

    public static void main(String[] args) {
        String str1 = "abc";
        String str2 = "abd";
        System.out.println(edit(str1,str2));
    }
}

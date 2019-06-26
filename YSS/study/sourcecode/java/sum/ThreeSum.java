package sum;

import java.util.*;

/**
 * @author yuh
 * @date 2019-06-25 06:48
 **/
public class ThreeSum {

    public static List<int[]> threeSum(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        ArrayList<int[]> list = new ArrayList<>();
        int index = 0;
        for (int i : arr) {
            map.put(i, index++);
        }
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                Integer index1 = map.get(k - arr[i] - arr[j]);
                if (index1 != null && index1 > j) {
                    int[] rt = new int[3];
                    rt[0] = i;
                    rt[1] = j;
                    rt[2] = index1;
                    list.add(rt);
                }
            }
        }
        return list;
    }

    public static List<int[]> threeSum1(int[] arr, int k) {
        Arrays.sort(arr);
        ArrayList<int[]> list = new ArrayList<>();
        for (int i = 0; i < arr.length - 2; i++) {
            int l = i + 1, r = arr.length - 1;
            while (l < r) {
                if (arr[i] + arr[l] + arr[r] < k) {
                    l += 1;
                } else if (arr[i] + arr[l] + arr[r] > k) {
                    r -= 1;
                } else {
                    int[] rt = new int[3];
                    rt[0] = i;
                    rt[1] = l;
                    rt[2] = r;
                    list.add(rt);
                    int tempL = l;

                    while (l < r && arr[l] == arr[l + 1]) {
                        int[] rtl = new int[3];
                        rt[0] = i;
                        rt[1] = l + 1;
                        rt[2] = r;
                        l++;
                        list.add(rtl);
                    }
                    while (l < r && arr[r] == arr[r - 1]) {
                        int[] rtr = new int[3];
                        rt[0] = i;
                        rt[1] = tempL;
                        rt[2] = r - 1;
                        l++;
                        list.add(rtr);
                    }
                    if (l == rt[1]) {
                        l++;
                    }
                    if (r == rt[2]) {
                        r--;
                    }
                }
            }
        }
        return list;
    }


    public static void main(String[] args) {
        int[] arr = {2, 5, -7, 9, 0, 14};
        List<int[]> list = threeSum1(arr, 7);
        for (int[] a : list) {
            System.out.println(Arrays.toString(a));
        }
    }
}

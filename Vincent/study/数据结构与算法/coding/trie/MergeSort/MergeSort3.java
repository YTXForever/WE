package MergeSort;

public class MergeSort3 {

    private static int reverseOrder = 0;

    public static void sort(int[] arr) {
        for (int sz = 1; sz < arr.length; sz *= 2) {
            for (int j = 0; j + sz < arr.length; j += (sz * 2)) {
                merge(arr, j, j + sz - 1, Math.min(arr.length - 1, j + sz + sz - 1));
            }
        }
    }

    /**
     * arr[start, mid] 与 arr[mid + 1, end] 归并排序
     */
    public static void merge(int[] arr, int start, int mid, int end) {
        int[] copyArr = new int[end - start + 1];
        System.arraycopy(arr, start, copyArr, 0, end - start + 1);

        int left = start;
        int right = mid + 1;

        for (int dest = start; dest <= end; dest++) {
            if (left > mid) {
                arr[dest] = copyArr[right - start];
                right++;
            } else if (right > end) {
                arr[dest] = copyArr[left - start];
                left++;
            } else if (copyArr[left - start] <= copyArr[right - start]) {
                arr[dest] = copyArr[left - start];
                left++;
            } else {
                arr[dest] = copyArr[right - start];
                right++;
                reverseOrder += (mid - left + 1);
            }
        }

    }

    public static void main(String[] args) {
        int[] arr = {8, 7, 6, 5, 4, 3, 2, 1};
        sort(arr);
        System.out.println("逆序对个数：" + reverseOrder);
    }

}

package recall;

/**
 * 背包问题
 * 回溯就是到一个分叉的多种选择 有可能是能直接列举出来的选择 也有可能是需要使用循环逐个检查
 * 一般来说还会判断是否这个分支应成功 如果成功的话 会阻断后面分支的执行
 *
 * @author yuh
 * @date 2019-06-07 09:05
 **/
public class Bag {

    public int cw = Integer.MIN_VALUE;

    public void bag(int w, int cw, int[] item, int index) {
        System.out.println("w:" + w + ",cw:" + cw + ",index:" + index);
        if (cw == w || item.length == index) {
            if (cw > this.cw) {
                this.cw = cw;
            }
            return;
        }
        //不放当前
        bag(w, cw, item, index + 1);
        //放入当前
        if (w >= cw + item[index]) {
            bag(w, cw + item[index], item, index + 1);
        }
    }


    public static void main(String[] args) {
        int[] arr = {1, 3, 5};
        Bag bag = new Bag();
        bag.bag(4, 0, arr, 0);
        System.out.println(bag.cw);

    }


}

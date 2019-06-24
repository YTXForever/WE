package slidewin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 抽象：
 *
 * 滑动窗口问题一般都有个前界有个后界  这个界一般还是固定大小的  我们要做的就是维护界内的信息 并用界内的信息做逻辑
 *
 * 操作滑动窗口的中心思想就行  用一个容器保存当前窗口内的元素
 * 1.判断队列中第一个元素是否应该已经在窗口外了
 * 2.把当前元素进去
 * 3.里面具体的逻辑具体实现
 * @author yuh
 * @date 2019-06-24 07:51
 **/
public class Max {

    public static List<Integer> max(List<Integer> list,int k){
        if(list == null || list.isEmpty()){
            return null;
        }
        LinkedList<Integer> queue = new LinkedList<>();
        LinkedList<Integer> res = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            Integer num = list.get(i);
            //判断第一个元素是否已经在滑动窗口最外面了
            if(i >= k && queue.getFirst() <= i-k){
                queue.removeFirst();
            }
            //删除比当前值小的前面的值
            while(!queue.isEmpty() && queue.getLast() <=  num){
                queue.removeLast();
            }
            queue.addLast(num);
            if(i >= k-1){
                res.add(queue.getFirst());
            }
        }
        return res;
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(2);
        list.add(1);
        System.out.println(max(list,3));

    }
}

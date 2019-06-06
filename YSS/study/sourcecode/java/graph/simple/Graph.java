package graph.simple;

import java.util.LinkedList;

/**
 * 无权图的接口
 * @author yuh
 * @date 2019-06-05 06:45
 **/
public interface Graph {

    void add(int v,int w);
    LinkedList<Integer> adj(int v);
    int v();
    int e();
    boolean directly();
}

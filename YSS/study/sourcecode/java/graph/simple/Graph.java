package graph.simple;

/**
 * 无权图的接口
 * @author yuh
 * @date 2019-06-05 06:45
 **/
public interface Graph {

    void add(int v,int w);
    Iterable<Integer> adj(int v);
    int v();
    int e();
    boolean directly();
}

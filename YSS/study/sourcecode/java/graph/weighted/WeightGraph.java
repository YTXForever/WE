package graph.weighted;

import graph.simple.Edge;

/**
 * 带权图
 * @author yuh
 * @date 2019-06-05 07:59
 **/
public interface WeightGraph {

    void add(int v,int w,int weight);
    Iterable<Edge> adj(int v);
    int v();
    int e();
    boolean directly();

}

package teacherwang;

import java.util.ArrayList;
import java.util.List;

/**
 * 最小生成树
 *
 * @author yuh
 * @date 2019-06-07 17:18
 **/
public class MST {


    private Graph graph;
    private IndexHeap<Integer> indexHeap;
    private boolean[] marked;
    private Edge[] edgeTo;


    public MST(Graph graph) {
        this.graph = graph;
        indexHeap = new IndexHeap<>(graph.v());
        marked = new boolean[graph.v()];
        edgeTo = new Edge[graph.v()];
    }

    public List<Edge> prim() {
        ArrayList<Edge> mst = new ArrayList<>();
        visit(0);
        while (!indexHeap.isEmpty()) {
            int i = indexHeap.removeIndex();
            Edge edge = edgeTo[i];
            mst.add(edge);
            visit(i);
        }
        return mst;
    }

    private void visit(int v) {
        marked[v] = true;
        List<Edge> adjcent = graph.adjcent(v);
        for (Edge edge : adjcent) {
            int other = edge.other(v);
            if (!marked[other]) {
                boolean contains = indexHeap.contains(other);
                if (contains) {
                    //已经添加过了
                    Integer original = indexHeap.get(other);
                    if (edge.getWeight() < original) {
                        indexHeap.change(other, edge.getWeight());
                        edgeTo[other] = edge;
                    }
                } else {
                    //还没添加过
                    indexHeap.add(other, edge.getWeight());
                    edgeTo[other] = edge;
                }
            }
        }
    }
}

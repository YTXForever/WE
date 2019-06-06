package graph.weighted;

import graph.simple.Edge;
import graph.simple.Graph;
import graph.simple.Search;
import graph.simple.SparseGraph;
import heap.IndexHeap;

import java.util.LinkedList;
import java.util.List;

/**
 * 单源最短路径算法
 *
 * @author yuh
 * @date 2019-06-06 07:29
 **/
public class Dijstra {

    private WeightGraph graph;

    public Dijstra(WeightGraph graph) {
        this.graph = graph;
    }

    public List<Integer> dijstra(int s, int t) {
        IndexHeap<Integer> indexHeap = new IndexHeap<>(graph.v());
        indexHeap.add(s, 0);
        int[] prev = new int[graph.v()];
        while (!indexHeap.isEmpty()) {
            int i = indexHeap.peekIndex();
            Integer currWeight = indexHeap.get(i);
            indexHeap.removeIndex();
            if (i == t) {
                LinkedList<Integer> list = new LinkedList<>();
                parseList(prev, s, t, list);
                return list;
            }
            Iterable<Edge> edges = graph.adj(i);
            for (Edge edge : edges) {
                int other = edge.other(i);
                Integer otherWeight = indexHeap.get(other);
                if (otherWeight == null) {
                    indexHeap.add(other, currWeight + edge.getWeight());
                    prev[other] = i;
                } else {
                    if (currWeight + edge.getWeight() < otherWeight) {
                        indexHeap.change(other, currWeight + edge.getWeight());
                        prev[other] = i;
                    }
                }
            }
        }
        return null;
    }


    public List<Integer> astar(int s, int t) {
        IndexHeap<Integer> indexHeap = new IndexHeap<>(graph.v());
        indexHeap.add(s, 0);
        int[] prev = new int[graph.v()];
        while (!indexHeap.isEmpty()) {
            int i = indexHeap.peekIndex();
            Integer currWeight = indexHeap.get(i);
            indexHeap.removeIndex();
            Iterable<Edge> edges = graph.adj(i);
            for (Edge edge : edges) {
                int other = edge.other(i);
                if (other == t) {
                    prev[other] = i;
                    LinkedList<Integer> list = new LinkedList<>();
                    parseList(prev, s, t, list);
                    return list;
                }
                Integer otherWeight = indexHeap.get(other);
                int newWeight = currWeight + edge.getWeight()
                        + mahatten(((SparseWeightGraph) graph).get(i), ((SparseWeightGraph) graph).get(other));
                if (otherWeight == null) {
                    indexHeap.add(other, newWeight);
                    prev[other] = i;
                } else {
                    if (newWeight < otherWeight) {
                        indexHeap.change(other, newWeight);
                        prev[other] = i;
                    }
                }
            }
        }
        return null;
    }


    private int mahatten(SparseWeightGraph.Vertex a, SparseWeightGraph.Vertex b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }


    private void parseList(int[] prev, int s, int t, LinkedList<Integer> list) {
        if (t == s) {
            list.addFirst(s);
        } else {
            list.addFirst(t);
            t = prev[t];
            parseList(prev, s, t, list);
        }
    }

    public static void main(String[] args) {
        WeightGraph graph = new SparseWeightGraph(5, true);
        ((SparseWeightGraph) graph).set(1,1,1);
        ((SparseWeightGraph) graph).set(2,2,2);
        ((SparseWeightGraph) graph).set(3,3,3);
        ((SparseWeightGraph) graph).set(4,-10,-10);

        graph.add(1, 2, 1);
        graph.add(1, 4, 0);
        graph.add(4, 3, 1);
        graph.add(2, 3, 1);
        Dijstra search = new Dijstra(graph);
        List<Integer> list = search.astar(1, 3);
        System.out.println(list);
    }
}

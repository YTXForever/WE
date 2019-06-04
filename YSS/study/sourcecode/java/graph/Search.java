package graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 无向图深度优先搜索和广度优先搜索
 *
 * @author yuh
 * @date 2019-06-05 07:12
 **/
public class Search {

    private Graph graph;

    public Search(Graph graph) {
        this.graph = graph;
    }


    public List<Integer> dsfSearch(int s, int t) {
        int[] prev = new int[graph.v()];
        boolean[] visited = new boolean[graph.v()];
        AtomicBoolean b = new AtomicBoolean();
        recDsf(s, t, prev, visited, b);
        if (b.get()) {
            LinkedList<Integer> list = new LinkedList<>();
            parseList(prev, s, t, list);
            return list;
        }
        return null;
    }

    public List<Integer> bfsSearch(int s, int t) {
        int[] prev = new int[graph.v()];
        boolean[] visited = new boolean[graph.v()];
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addLast(s);
        while (!queue.isEmpty()) {
            Integer i = queue.removeFirst();
            visited[i] = true;
            Iterable<Integer> adj = graph.adj(i);
            for (Integer sub : adj) {
                if (!visited[sub]) {
                    prev[sub] = i;
                    if (sub == t) {
                        LinkedList<Integer> list = new LinkedList<>();
                        parseList(prev, s, t, list);
                        return list;
                    }
                    queue.addLast(sub);
                }
            }
        }
        return null;
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

    private void recDsf(int s, int t, int[] prev, boolean[] visited, AtomicBoolean found) {
        if (found.get()) {
            return;
        }
        visited[s] = true;
        if (s == t) {
            found.set(true);
            return;
        }
        Iterable<Integer> adj = graph.adj(s);
        for (Integer sub : adj) {
            if (!visited[t]) {
                prev[sub] = s;
                recDsf(sub, t, prev, visited, found);
            }
        }
    }

    public static void main(String[] args) {
        Graph graph = new SparseGraph(5, true);
        graph.add(1, 2);
        graph.add(2, 3);
        graph.add(3, 4);
        Search search = new Search(graph);
        List<Integer> list = search.bfsSearch(1, 4);
        System.out.println(list);
    }
}

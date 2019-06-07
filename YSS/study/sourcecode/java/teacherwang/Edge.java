package teacherwang;

/**
 * 边
 *
 * @author yuh
 * @date 2019-06-07 17:01
 **/
public class Edge {

    private int v;
    private int w;
    private int weight;

    public Edge(int v, int w, int weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int other(int v) {
        return v == this.v ? w : this.v;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Edge{");
        sb.append("v=").append(v);
        sb.append(", w=").append(w);
        sb.append(", weight=").append(weight);
        sb.append('}');
        return sb.toString();
    }
}

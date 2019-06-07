package teacherwang;

import java.util.List;

/**
 * 图
 *
 * @author yuh
 * @date 2019-06-07 16:59
 **/
public interface Graph {

    /**
     * 顶点的个数
     *
     * @return
     */
    int v();

    /**
     * 边的个数
     *
     * @return
     */
    int e();

    /**
     * 连接两个顶点
     *
     * @param v
     * @param w
     * @param weight
     */
    void connect(int v, int w, int weight);

    /**
     * 返回这个顶点相连的所有的边
     *
     * @param v
     * @return
     */
    List<Edge> adjcent(int v);
}

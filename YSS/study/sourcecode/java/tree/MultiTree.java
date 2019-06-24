package tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuh
 * @date 2019-06-13 21:41
 **/
public class MultiTree {

    static class Node {
        char data;
        List<Node> subNodes = new ArrayList<>();

        public Node(char data) {
            this.data = data;
        }
    }


    static String printTree(Node root) {
        if (root == null) {
            return null;
        }
        if (root.subNodes.isEmpty()) {
            return root.data + "";
        }
        String all = root.data + "(";
        for (int i = 0; i < root.subNodes.size(); i++) {
            all+=printTree(root.subNodes.get(i));
            if(i != root.subNodes.size()-1){
                all+=",";
            }
        }
        return all+")";
    }

    public static void main(String[] args) {
        Node root = new Node('a');
        Node b = new Node('b');
        Node c = new Node('c');
        Node d = new Node('d');
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(b);
        nodes.add(c);
        nodes.add(d);
        root.subNodes = nodes ;


        Node e = new Node('e');
        Node f = new Node('f');
        Node g = new Node('g');
        ArrayList<Node> nodes1 = new ArrayList<>();
        nodes1.add(e);
        nodes1.add(f);
        nodes1.add(g);
        b.subNodes = nodes1 ;
        System.out.println(printTree(root));
    }
}

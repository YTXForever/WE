package str;

import java.util.*;

/**
 * 前缀树
 *
 * @author yuh
 * @date 2019-06-02 18:37
 **/
public class Trie {

    private class Node {
        private char data;
        private Map<Character, Node> subNodeMap = new TreeMap<>();
        private boolean isEnd;
        private Node fail;
        private int length;

        public Node get(char c) {
            return subNodeMap.get(c);
        }

        public void put(char c, Node node) {
            subNodeMap.put(c, node);
        }

        public Node(char data) {
            this.data = data;
        }
    }

    private Node root = new Node('\0');
    private int size;

    public void add(String word) {
        Node curr = root;
        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            Node node = curr.get(aChar);
            if (node == null) {
                node = new Node(aChar);
                curr.put(aChar, node);
            }
            if (i == chars.length - 1 && !node.isEnd) {
                node.isEnd = true;
                node.length = chars.length;
            }
            curr = node;
        }
    }

    public boolean hasPrefix(String prefix) {
        Node curr = root;
        char[] chars = prefix.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            Node node = curr.get(aChar);
            if (node == null) {
                return false;
            }
            curr = node;
        }
        return true;
    }


    public List<String> startWith(String prefix) {
        Node curr = root;
        char[] chars = prefix.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            Node node = curr.get(aChar);
            if (node == null) {
                return null;
            }
            curr = node;
        }
        ArrayList<String> list = new ArrayList<>();
        collect(curr, prefix, list);
        return list;
    }


    private void buildFailTree() {
        LinkedList<Node> nodes = new LinkedList<>();
        nodes.addFirst(root);
        while (!nodes.isEmpty()) {
            Node node = nodes.removeLast();
            Map<Character, Node> subNodeMap = node.subNodeMap;
            for (Node sub : subNodeMap.values()) {
                if (node == root) {
                    sub.fail = root;
                } else {
                    Node fail = null;
                    Node parentFail = node.fail;
                    while (parentFail != null) {
                        Node node1 = parentFail.get(sub.data);
                        if (node1 != null) {
                            fail = node1;
                            break;
                        }
                        parentFail = parentFail.fail;
                    }
                    sub.fail = fail == null ? root : fail;
                }
                nodes.addFirst(sub);
            }
        }
    }

    private List<String> matchAll(String sentence,boolean all) {
        Node curr = root;
        ArrayList<String> list = new ArrayList<>();
        char[] chars = sentence.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            while (curr.get(aChar) == null && curr != root) {
                curr = curr.fail;
            }
            Node tmp = curr.get(aChar);
            curr = tmp == null ? root : tmp;
            while (tmp != null && tmp != root) {
                if(tmp.isEnd){
                    list.add(sentence.substring(i - tmp.length + 1, i + 1));
                }
                if(!all){
                    break;
                }
                tmp = tmp.fail;
            }
        }
        return list;
    }

    private void collect(Node curr, String prefix, ArrayList<String> list) {
        if (curr.isEnd) {
            list.add(prefix);
        }
        Map<Character, Node> subNodeMap = curr.subNodeMap;
        for (Node node : subNodeMap.values()) {
            collect(node, prefix + node.data, list);
        }
    }


    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.add("YSS");
        trie.add("YTX");
        trie.buildFailTree();
        System.out.println(trie.matchAll("YSS❤YTX",true));
    }
}

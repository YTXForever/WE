package str;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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


    public List<String> startWith(String prefix){
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
        collect(curr,prefix,list);
        return list;
    }

    private void collect(Node curr, String prefix, ArrayList<String> list) {
        if(curr.isEnd){
            list.add(prefix);
        }
        Map<Character, Node> subNodeMap = curr.subNodeMap;
        for (Node node : subNodeMap.values()) {
            collect(node,prefix+node.data,list);
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.add("a");
        trie.add("ab");
        trie.add("abc");
        trie.add("abcd");
        trie.add("b");
        trie.add("bc");
        trie.add("bcd");
//        System.out.println(trie.hasPrefix("a"));
//        System.out.println(trie.hasPrefix("ab"));
//        System.out.println(trie.hasPrefix("ad"));
//        System.out.println(trie.hasPrefix("abc"));
//        System.out.println(trie.hasPrefix("b"));
//        System.out.println(trie.hasPrefix("bc"));
        System.out.println(trie.startWith("a"));
    }
}

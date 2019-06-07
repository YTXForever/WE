package trie;

import java.util.ArrayList;
import java.util.List;

public class Trie1 {

    private TrieNode1 root;

    public void insert(String word) {
        char[] chars = word.toCharArray();
        TrieNode1 node = root;
        for (int i = 0; i < chars.length; i++) {
            TrieNode1 son = node.getSons()[chars[i] - 'a'];
            if (son == null) {
                son = new TrieNode1(new TrieNode1[26], chars[i], false);
                node.getSons()[chars[i] - 'a'] = son;
            }
            node = son;
        }
        node.setEnd(true);
    }

    public boolean has(String word) {
        char[] chars = word.toCharArray();
        TrieNode1 node = root;
        for (int i = 0; i < chars.length; i++) {
            TrieNode1 son = node.getSons()[chars[i] - 'a'];
            if (son == null) {
                return false;
            }
            node = son;
        }
        return node.isEnd();
    }

    private TrieNode1 wordNode(String word) {
        char[] chars = word.toCharArray();
        TrieNode1 node = root;
        for (int i = 0; i < chars.length; i++) {
            TrieNode1 son = node.getSons()[chars[i] - 'a'];
            if (son == null) {
                return null;
            }
            node = son;
        }
        return node;
    }

    public List<String> hasPrefix(String prefix) {
        List<String> words = new ArrayList<String>();
        preTraverse(wordNode(prefix), prefix, words);
        return words;
    }

    private void preTraverse(TrieNode1 node, String prefix, List<String> words) {
        if (node == null) {
            return;
        }
        TrieNode1[] sons = node.getSons();
        for (TrieNode1 son : sons) {
            if (son == null) {
                continue;
            }
            if (son.isEnd()) {
                words.add(prefix + son.getVal());
            }
            preTraverse(son, prefix + son.getVal(), words);
        }
    }


    public static void main(String[] args) {
        String[] words = {"bad", "bag", "bla", "beg", "bal", "bed", "abed"};

        Trie1 trie1 = new Trie1();
        for (String word : words) {
            trie1.insert(word);
        }

        System.out.println("bad is has " + trie1.has("bad"));
        System.out.println("bag is has " + trie1.has("bag"));
        System.out.println("bla is has " + trie1.has("bla"));
        System.out.println("beg is has " + trie1.has("beg"));
        System.out.println("bal is has " + trie1.has("bal"));
        System.out.println("bed is has " + trie1.has("bed"));
        System.out.println("abed is has " + trie1.has("abed"));


        List<String> list = trie1.hasPrefix("ba");
        for (String str : list) {
            System.out.println(str);
        }

    }

    public Trie1() {
        this.root = new TrieNode1(new TrieNode1[26], ' ', false);
    }
}

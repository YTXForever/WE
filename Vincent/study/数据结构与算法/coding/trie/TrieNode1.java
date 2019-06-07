package trie;

public class TrieNode1 {
    private TrieNode1[] sons;
    private char val;
    private boolean isEnd;

    public TrieNode1(TrieNode1[] sons, char val, boolean isEnd) {
        this.sons = sons;
        this.val = val;
        this.isEnd = isEnd;
    }

    public TrieNode1[] getSons() {
        return sons;
    }

    public void setSons(TrieNode1[] sons) {
        this.sons = sons;
    }

    public char getVal() {
        return val;
    }

    public void setVal(char val) {
        this.val = val;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}

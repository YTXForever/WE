package com.ytx.we.binary;

public class  BinaryTreeNode {

    private int key;
    private String value;
    private BinaryTreeNode left;
    private BinaryTreeNode right;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BinaryTreeNode getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode left) {
        this.left = left;
    }

    public BinaryTreeNode getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode right) {
        this.right = right;
    }
    public BinaryTreeNode clone(){
        BinaryTreeNode des = new BinaryTreeNode();
        des.setKey(this.getKey());
        des.setValue(this.getValue());
        des.setLeft(this.getLeft());
        des.setRight(this.getRight());
        return des;
    }

    @Override
    public String toString() {
        return "BinaryTreeNode{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}

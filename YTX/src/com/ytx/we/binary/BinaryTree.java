package com.ytx.we.binary;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree {

    private BinaryTreeNode root;
    private int count =0;

    public BinaryTreeNode getRoot() {
        return root;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRoot(BinaryTreeNode root) {
        this.root = root;
    }
    public void insert(int key,String value){
        root = insert(root,key,value);
    }
    private BinaryTreeNode insert(BinaryTreeNode root,int key,String value){
        if(root == null){
            root = new BinaryTreeNode();
            root.setKey(key);
            root.setValue(value);
            return root;
        }
        if( key ==root.getKey()){
            root.setValue(value);
            return root;
        }
        if(key >root.getKey()){
            BinaryTreeNode right = insert(root.getRight(),key,value);
            root.setRight(right);
             return root;
        }else {
            BinaryTreeNode left = insert(root.getLeft(),key,value);
            root.setLeft(left);
            return root;
        }

    }
    public void search(){
        search(this.getRoot());
    }
    private void search(BinaryTreeNode root){
        if(root != null){
            search(root.getLeft());
            System.out.print("["+root.getKey()+"]");
            search(root.getRight());
        }
    }
    public BinaryTreeNode find(int key){
        return find(this.root,key);
    }
    private BinaryTreeNode find(BinaryTreeNode root,int key){
        if(root == null){
            return null;
        }
        if(root.getKey() == key){
            return root;
        }
        if(root.getKey() > key){
            //left
            return find(root.getLeft(),key);
        }else {
            //right
            return find(root.getRight(),key);
        }
    }
    public void preOrder(){
        preOrder(this.root);
    }
    private void preOrder(BinaryTreeNode root){
        Stack<BinaryTreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.empty()){
            BinaryTreeNode node = stack.pop();
            if(node != null){
                System.out.print("["+node.getKey()+"]");
                stack.push(node.getRight());
                stack.push(node.getLeft());
            }


        }

    }

    public void levelOrder(){
        levelOrder(this.root);
    }
    private void levelOrder(BinaryTreeNode root){
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            BinaryTreeNode node = queue.poll();
            if(node != null){
                System.out.print("["+node.getKey()+"]");
                queue.offer(node.getLeft());
                queue.offer(node.getRight());
            }

        }

    }

    public void removeMin(){
        root = removeMin(this.root);
    }
    private BinaryTreeNode removeMin(BinaryTreeNode root){
        if(root == null){
            return null;
        }
        if(root.getLeft() == null){
            //最小节点
            return root.getRight();
        }else{
            root.setLeft(removeMin(root.getLeft()));
        }
        return root;
    }
    public void removeMax(){
        //removeMax(this.root,null);
        //root = removeMax(this.root);
        removeMaxbystack(this.root);
    }
    private void removeMax(BinaryTreeNode root,BinaryTreeNode parentNode){
        if(root == null){
            return;
        }
        if(root.getRight() == null){
            //最大节点
            parentNode.setRight(root.getLeft());
        }else{
            removeMax(root.getRight(),root);
        }

    }
    private BinaryTreeNode removeMax(BinaryTreeNode root){
        if(root == null){
            return null;
        }
        BinaryTreeNode right =root.getRight();
        BinaryTreeNode parent = root;
        while(right != null && right.getRight() != null){
            parent = right;
            right = right.getRight();
        }
        //最大节点
        parent.setRight(right.getLeft());
        return root;

    }
    private BinaryTreeNode removeMaxbystack(BinaryTreeNode root){
        if(root == null){
            return null;
        }

        BinaryTreeNode right =root.getRight();
        Stack<BinaryTreeNode> stack = new Stack<>();
        stack.push(right);

        while(right != null && right.getRight() != null){
            right = right.getRight();
            stack.push(right);
        }
        if(!stack.isEmpty()){
            BinaryTreeNode maxNode = stack.pop();
            if(!stack.isEmpty()){
                BinaryTreeNode maxParent = stack.pop();
                maxParent.setRight(maxNode.getLeft());
                ;
            }else{
                root = maxNode.getLeft();
            }

        }

        return root;

    }

    public void remove(int key){
        root = remove(this.root,key);
    }
    private  BinaryTreeNode remove(BinaryTreeNode root,int key){
        if(root == null){
            return root;
        }
        if(root.getKey() == key){
            if(root.getRight() == null ){
                //右子树为空，左子树不确定
                root = root.getLeft();
                return root;
            }
            if(root.getLeft() == null ){
                //左子树为空，右子树不确定
                root = root.getRight();
                return root;
            }
            //左子树右子树都不为空
            BinaryTreeNode minNode = findMin(root.getRight()).clone();
            root.setRight(removeMin(root.getRight()));
            root.setKey(minNode.getKey());
            root.setValue(minNode.getValue());
            return root;

        }
        if(root.getKey() > key){
            root.setLeft(remove(root.getLeft(),key));
        }else{
            root.setRight(remove(root.getRight(),key));
        }
        return root;

    }
    private BinaryTreeNode findMin(BinaryTreeNode root){
        if(root == null){
            return null;
        }
        while(root.getLeft() != null){
            root = root.getLeft();
        }
        return  root;

    }
    public int getLayer(){
        return getLayer(root);

    }
    private int getLayer(BinaryTreeNode root){
        if(root == null){
            return 0;
        }
        int left = getLayer(root.getLeft());
        int right = getLayer(root.getRight());
        return 1+(left>right?left:right);

    }


}

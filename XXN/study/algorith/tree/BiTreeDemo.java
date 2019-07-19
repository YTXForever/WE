package com.daojia.testHY.althority;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public class BiTreeDemo {

	static class Node<T>{
		private Node<T> leftChild;
		private Node<T> rightChild;
		private T data;
		public Node(T data){
			this.data = data;
		}
		public Node(Node<T> leftChild,Node<T> rightChild,T data){
			this.leftChild = leftChild;
			this.rightChild = rightChild;
			this.data = data;
		}
		public Node<T> getLeftChild() {
			return leftChild;
		}
		public void setLeftChild(Node<T> leftChild) {
			this.leftChild = leftChild;
		}
		public Node<T> getRightChild() {
			return rightChild;
		}
		public void setRightChild(Node<T> rightChild) {
			this.rightChild = rightChild;
		}
		public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data;
		}
	}
	/**
	 *          0
	 *      1       2
	 *    3   4   5   6
	 *   7 8 9 10 
	 *   构建二叉树
	 * */
	public Node<Integer> buildTree(int[] arr){
		List<Node<Integer>> nodeList = new ArrayList<>();
		for(int i = 0;i<arr.length;i++){
			nodeList.add(new Node<Integer>(arr[i]));
		}
		for(int i = 0;i<=arr.length/2-1;i++){
			nodeList.get(i).leftChild = nodeList.get(2*i+1);
			if(2*i+2<arr.length){
				nodeList.get(i).rightChild = nodeList.get(2*i+2);
			}
		}
		return nodeList.get(0);
	}
	
	
	/**
	 * 先序遍历
	 * 根-左-右
	 * */
	public void preOrder(Node<Integer> node){
		System.out.println(node.getData());
		if(node.getLeftChild()!=null){
			preOrder(node.getLeftChild());
		}
		if(node.getRightChild()!=null){
			preOrder(node.getRightChild());
		}
		
	}
	
	/**
	 * 中序遍历
	 * */
	public void midOrder(Node<Integer> node){
		if(node.getLeftChild()!=null){
			midOrder(node.getLeftChild());
		}
		System.out.println(node.getData());
		if(node.getRightChild()!=null){
			midOrder(node.getRightChild());
		}
	}
	/**
	 * 后序遍历
	 * */
	public void postOrder(Node<Integer> node){
		if(node.getLeftChild()!=null){
			postOrder(node.getLeftChild());
		}
		
		if(node.getRightChild()!=null){
			postOrder(node.getRightChild());
		}
		
		System.out.println(node.getData());
	}
	
	/**
	 * 层次遍历
	 * 从上至下  队列
	 * 从下至上  栈
	 * */
	public void levelOrder(Node<Integer> root){
		Deque<Node<Integer>> queue = new ArrayDeque<>();
		queue.add(root);
		while(!queue.isEmpty()){
			
			Node<Integer> node = queue.pop();
			System.out.println(node.getData());
			if(node.getLeftChild()!=null){
				queue.add(node.getLeftChild());
			}
			
			if(node.getRightChild()!=null){
				queue.add(node.getRightChild());
			}
		}
	}
	public static void main(String[] args) {
		int[] arr = new int[]{1,3,5,6,9,10};
		BiTreeDemo demo = new BiTreeDemo();
		demo.levelOrder(demo.buildTree(arr));
	}
}

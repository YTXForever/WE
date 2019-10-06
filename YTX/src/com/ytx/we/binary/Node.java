package com.ytx.we.binary;

public class Node<K,V> {
    private K key;
    private V value;
    private Node<K,V> pre;
    private Node<K,V> next;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Node<K, V> getPre() {
        return pre;
    }

    public void setPre(Node<K, V> pre) {
        this.pre = pre;
    }

    public Node<K, V> getNext() {
        return next;
    }

    public void setNext(Node<K, V> next) {
        this.next = next;
    }
}

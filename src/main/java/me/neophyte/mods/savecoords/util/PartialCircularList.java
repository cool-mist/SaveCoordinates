package me.neophyte.mods.savecoords.util;

public class PartialCircularList<T> {
    
    public static class Node<V> {
        V value;
        Node<V> next;

        Node(V v) {
            this.value = v;
        }
    }

    Node<T> head = null;
    Node<T> tail = null;
    Node<T> current = null;

    public void add(T t) {
        if (head == null) {
            head = new Node<T>(t);
            tail = head;
            head.next = tail;
            tail.next = head;
            return;
        }

        Node<T> temp = new Node<T>(t);
        temp.next = head;
        tail.next = temp;
        tail = temp;
    }

    public T next() {
        if (current == null) {
            current = head;
        } else {
            current = current.next;
        }

        return current.value;
    }

    public T current() {
        if (current == null) {
            return null;
        }
        return current.value;
    }
}

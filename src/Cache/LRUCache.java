package Cache;

import java.util.Map;
import java.util.HashMap;

public class LRUCache <T, V> {
    private class Node {
        private Node prev, next;
        private T key;
        private V val;

        Node(T key, V val) {
            this.key = key;
            this.val = val;
            prev = next = null;
        }
    }

    private Node head, tail;
    private int capacity, size;
    private Map<T, Node> map;

    public LRUCache(int capacity) {
        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        head.next = tail;
        tail.prev = head;

        this.capacity = capacity;
        this.map = new HashMap<>();
        this.size = 0;
    }

    public V get(T key) {
        if(!map.containsKey(key))
            return null;

        Node n = map.get(key);
        V ret = n.val;
        this.deleteNode(n);
        this.addHead(n);
        return ret;
    }

    public boolean contains(T key) {
        return map.containsKey(key);
    }

    public void put(T key, V value) {
        if(map.containsKey(key)) {
            Node n = map.get(key);
            n.val = value;
            this.deleteNode(n);
            this.addHead(n);
            return;
        }

        Node put = new Node(key, value);
        map.put(key, put);
        if(this.size >= this.capacity) {
            System.out.println("evict");
            map.remove(tail.prev.key);
            this.deleteNode(tail.prev);
        }
        else {
            this.size++;
        }
        this.addHead(put);
    }

    private void deleteNode(Node n) {
        n.prev.next = n.next;
        n.next.prev = n.prev;
    }

    private void addHead(Node n) {
        n.next = head.next;
        n.prev = head;
        n.prev.next = n;
        n.next.prev = n;
    }
}

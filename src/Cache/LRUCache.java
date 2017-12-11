package Cache;

import java.util.Map;
import java.util.HashMap;

import Disk.Disk;

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
    private int capacity;

    private int size;
    private Disk<T, V> disk;

    private int numEvict;


    private int numHit;
    private Map<T, Node> map;

    public LRUCache(Disk <T, V>disk, int capacity) {
        this.disk = disk;
        this.init(capacity);
    }

    public LRUCache(int capacity) {
        this.disk = new Disk<T, V>();
        this.init(capacity);
    }

    public int getNumEntriesLeft() {
        return capacity - size;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumHit() {
        return numHit;
    }

    public int getNumEvict() {
        return numEvict;
    }

    public synchronized BlockInfo<V> get(T key) {
        if(!map.containsKey(key)) {
            //When cache miss, put key and value into map
            this.numEvict++;
//            System.out.println(numEvict);
            V value = this.disk.fetchFromDisk(key);
            this.put(key, value);
            return new BlockInfo<>(true, value);
        }

        this.numHit++;
        Node n = map.get(key);
        V ret = n.val;
        this.deleteNode(n);
        this.addHead(n);
        return new BlockInfo<>(false, ret);
    }

    public boolean contains(T key) {
        return map.containsKey(key);
    }

    public synchronized void put(T key, V value) {
        if(map.containsKey(key)) {
            this.numHit++;
            Node n = map.get(key);
            n.val = value;
            this.deleteNode(n);
            this.addHead(n);
            return;
        }

        Node put = new Node(key, value);
        map.put(key, put);
        if(this.size >= this.capacity) {
            map.remove(tail.prev.key);
            this.deleteNode(tail.prev);
        }
        else {
            this.size++;
        }
        this.addHead(put);
    }

    private void init(int capacity) {

        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        head.next = tail;
        tail.prev = head;

        this.capacity = capacity;
        this.map = new HashMap<>();
        this.size = 0;
        this.numEvict = 0;
        this.numHit = 0;

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

package Disk;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Disk <K, V>{
    private Map<K, V> disk;
    private int numAccess;

    public Disk() {
        disk = new HashMap<>();
        numAccess = 0;
    }

    public void addToDisk(K key, V value) {
        disk.put(key, value);
    }

    public V fetchFromDisk(K key) {
        this.numAccess++;
        return disk.get(key);
    }

    public Map<K, V> getDisk() {
        return Collections.unmodifiableMap(disk);
    }

    public int getNumAccess() {
        return numAccess;
    }

}

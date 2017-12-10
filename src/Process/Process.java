package Process;

import Cache.LRUCache;
import Node.GraphNode;

import java.util.List;

public class Process {

    List<LRUCache<Float, Float>> partitionedCaches;

    public Process(List<LRUCache<Float, Float>> caches) {
        this.partitionedCaches = caches;
    }

    public void addToPartitionedCache(Float key, Float val) {
        int ind = this.hashKey(key);
        System.out.println(ind);
        LRUCache cache = this.partitionedCaches.get(ind);
        cache.put(key, val);
    }

    private int hashKey(Object key) {
        int hash = key.hashCode();
        return hash % this.partitionedCaches.size();
    }
}

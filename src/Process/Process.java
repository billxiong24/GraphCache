package Process;

import Cache.BlockInfo;
import Cache.LRUCache;
import Node.GraphNode;

import java.util.List;

public class Process {

    List<LRUCache<Float, GraphNode>> partitionedCaches;


    private int totalMisses, totalHits, numAccess;

    public Process(List<LRUCache<Float, GraphNode>> caches) {
        this.partitionedCaches = caches;
        this.totalMisses = 0;
        this.totalHits = 0;
        this.numAccess = 0;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public int getNumAccess() {
        return numAccess;
    }

    public int getTotalMisses() {
        return totalMisses;
    }

    public GraphNode retrieveFromPartitionedCache(Float key) {
        int ind = this.hashKey(key);
        LRUCache<Float, GraphNode> cache = this.partitionedCaches.get(ind);
        BlockInfo<GraphNode> val = cache.get(key);

        this.numAccess++;
        if(val.isMissed())
            this.totalMisses++;
        else
            this.totalHits++;

        return val.getValue();
    }

    public void addToPartitionedCache(Float key, GraphNode val) {
        int ind = this.hashKey(key);
        LRUCache<Float, GraphNode> cache = this.partitionedCaches.get(ind);
        cache.put(key, val);
    }

    private int hashKey(Object key) {
        int hash = key.hashCode();
        return hash % this.partitionedCaches.size();
    }
}

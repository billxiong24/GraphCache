package Process;

import Cache.BlockInfo;
import Cache.LRUCache;
import Node.GraphNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Process {

    private List<LRUCache<Float, GraphNode>> partitionedCaches;

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

    private int checkCaches(Float key) {
        for(int i = 0; i < this.partitionedCaches.size(); i++) {
            if(this.partitionedCaches.get(i).contains(key)) {
                return i;
            }
        }
        return -1;
    }

    public GraphNode retrieveFromPartitionedCache(Float key) {
        int check = checkCaches(key);
        int ind = check == -1 ? this.randCache(key) : check;

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

    //find the cache with most space left
    private int hashKey(Object key) {
        int max = 0;
        int ind = 0;
        for(int i = 0; i < this.partitionedCaches.size(); i++) {
            int numEntries = this.partitionedCaches.get(i).getNumEntriesLeft();
            if(max < numEntries) {
                max = numEntries;
                ind = i;
            }
        }

        if(max == 0) {
            return this.randCache(key);
        }
        return ind;
    }

    private int randCache(Object key) {
        List<Integer> indices = new ArrayList<>();
        for(int i = 0; i < this.partitionedCaches.size(); i++) {
            for(int j = 0; j < this.partitionedCaches.get(i).getCapacity(); j++) {
                indices.add(i);
            }
        }
        return indices.get((int) (Math.random() * indices.size()));
    }
}

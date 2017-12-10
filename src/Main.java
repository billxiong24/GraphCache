import Cache.LRUCache;
import Disk.Disk;
import Node.GraphNode;
import Process.Process;

import java.util.*;

public class Main {

    public static void main(String args[]) {
        GraphNode node = GraphGen.genRandGraph(3000, 6);

        Disk<Float, GraphNode> disk = DiskGen.genDisk(node);
        List<LRUCache<Float, GraphNode>> list = new ArrayList<>();
        List<LRUCache<Float, GraphNode>> list2 = new ArrayList<>();

        LRUCache<Float, GraphNode> cache = new LRUCache<Float, GraphNode>(disk, 20);
        LRUCache<Float, GraphNode> overlap = new LRUCache<Float, GraphNode>(disk, 100);
        LRUCache<Float, GraphNode> cache2 = new LRUCache<Float, GraphNode>(disk, 20);

        list.add(cache);
        list.add(overlap);

        list2.add(cache2);
        list2.add(overlap);

        Process process = new Process(list);
        Process process2 = new Process(list2);
        Queue<GraphNode> queue = new LinkedList<>();
        Set<GraphNode> visited = new HashSet<>();
        queue.add(node);

        while(!queue.isEmpty()) {
            GraphNode res = queue.poll();
            process.retrieveFromPartitionedCache(res.getVal());
            process2.retrieveFromPartitionedCache(res.getVal());
//            System.out.println(res.getVal());
            for(GraphNode temp : res.getChildren()) {
                if(!visited.contains(temp)) {
                    visited.add(temp);
                    queue.add(temp);
                }
            }
        }

        System.out.println(process.getTotalMisses());
        System.out.println(process2.getTotalMisses());


    }

}

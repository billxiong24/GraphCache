import Cache.LRUCache;
import Disk.Disk;
import GraphTraversal.BFS;
import GraphTraversal.DFS;
import GraphTraversal.GraphTraversal;
import Node.GraphNode;
import Process.Process;

import java.util.*;

public class Main {

    public static void main(String args[]) throws InterruptedException {
        GraphGen gen = new GraphGen(10000, 0.09);
        GraphNode node = gen.genRandGraph(12);
        GraphNode node2 = null;
        while(node2 == null) {
            node2 = gen.getRandNode(node, 2);
        }
        Disk<Float, GraphNode> disk = DiskGen.genDisk(node);

        List<LRUCache<Float, GraphNode>> list = new ArrayList<>();
        List<LRUCache<Float, GraphNode>> list2 = new ArrayList<>();

        int cap1 = 200;
        int cap2 = 400;
        LRUCache<Float, GraphNode> cache = new LRUCache<Float, GraphNode>(disk, cap1);
        LRUCache<Float, GraphNode> overlap = new LRUCache<Float, GraphNode>(disk, cap2);
        LRUCache<Float, GraphNode> cache2 = new LRUCache<Float, GraphNode>(disk, cap1);

        list.add(cache);
        list.add(overlap);

        list2.add(cache2);
        list2.add(overlap);

        Thread a = new Thread(() -> {
            Process p = new Process(list);
            BFS bfs = new BFS(node, p);
            bfs.traverse();
            System.out.println("p1 misses: " + p.getTotalMisses());
            System.out.println("p1 hits: "+ p.getTotalHits());
        });

        Thread b = new Thread(() -> {
            Process p2 = new Process(list2);
            GraphTraversal bfs2  = new BFS(node, p2);
            bfs2.traverse();
            System.out.println("p2 misses: " + p2.getTotalMisses());
            System.out.println("p2 hits: " + p2.getTotalHits());
        });

        b.start();
        a.start();

        a.join();
        b.join();
    }

}

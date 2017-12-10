import Cache.LRUCache;
import Disk.Disk;
import GraphTraversal.BFS;
import Node.GraphNode;
import Process.Process;

import java.util.*;

public class Main {

    public static void main(String args[]) {
        GraphGen gen = new GraphGen(4000, 0.04);
        GraphNode node = gen.genRandGraph(3);
        GraphNode node2 = null;
        while(node2 == null) {
            node2 = gen.getRandNode(node, 20);
        }
        Disk<Float, GraphNode> disk = DiskGen.genDisk(node);

        List<LRUCache<Float, GraphNode>> list = new ArrayList<>();
        List<LRUCache<Float, GraphNode>> list2 = new ArrayList<>();

        int cap1 = 200;
        int cap2 = 200;
        LRUCache<Float, GraphNode> cache = new LRUCache<Float, GraphNode>(disk, cap1);
        LRUCache<Float, GraphNode> overlap = new LRUCache<Float, GraphNode>(disk, cap2);
        LRUCache<Float, GraphNode> cache2 = new LRUCache<Float, GraphNode>(disk, cap1);

        list.add(cache);
        list.add(overlap);

        list2.add(cache2);
        list2.add(overlap);
        Process p = new Process(list);
        Process p2 = new Process(list2);

        BFS bfs = new BFS(node, p);
        BFS bfs2 = new BFS(node2, p2);
        bfs.traverse();
        bfs2.traverse();

        System.out.println(p.getTotalMisses());
        System.out.println(p2.getTotalMisses());
    }

}

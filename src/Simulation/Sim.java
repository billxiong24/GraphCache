package Simulation;

import Cache.LRUCache;
import Disk.Disk;
import GraphTraversal.*;
import Node.GraphNode;

import java.util.ArrayList;
import java.util.List;
import Process.Process;
import com.sun.corba.se.impl.orbutil.graph.Graph;

public class Sim {

    private interface Callback {
        void callback(Process process);
    }

    private int shared, separate;

    public Sim(int shared, int separate) {

        this.shared = shared;
        this.separate = separate;
    }

    public Thread genProcess(Disk<Float, GraphNode> disk, GraphNode node, LRUCache<Float, GraphNode> overlap, Callback callback) {
        LRUCache<Float, GraphNode> cache = new LRUCache<Float, GraphNode>(disk, separate);
        List<LRUCache<Float, GraphNode>> list = new ArrayList<>();
        list.add(cache);
        list.add(overlap);

        Thread thread = new Thread(() -> {
            Process p = new Process(list);
            GraphTraversal traversal = new BFS(node, p);
            traversal.traverse();
            callback.callback(p);
        });

        return thread;
    }

    public void runSim(int numNodes, double percentCycle, int maxFan) throws InterruptedException {

        GraphGen gen = new GraphGen(numNodes, percentCycle);
        GraphNode node = gen.genRandGraph(maxFan);
        GraphNode node2 = null;
        while(node2 == null) {
            node2 = gen.getRandNode(node, 2);
        }

        Disk<Float, GraphNode> disk = DiskGen.genDisk(node);
        LRUCache<Float, GraphNode> overlap = new LRUCache<Float, GraphNode>(disk, shared);

        Thread a = this.genProcess(disk, node, overlap, (process -> {
            System.out.println("P1 total hits: " + process.getTotalHits());
            System.out.println("P1 total misses: " + process.getTotalMisses());

        }));

        Thread b = this.genProcess(disk, node2, overlap, (process -> {
            System.out.println("P2 total hits: " + process.getTotalHits());
            System.out.println("P2 total misses: " + process.getTotalMisses());

        }));

        a.start();
        b.start();
    }
}

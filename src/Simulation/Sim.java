package Simulation;

import Cache.LRUCache;
import Disk.Disk;
import GraphTraversal.*;
import Node.GraphNode;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import Process.Process;

public class Sim {

    private interface Callback {
        void callback(Process process);
    }

    private int shared, separate;

    public Sim(int shared, int separate) {

        this.shared = shared;
        this.separate = separate;
    }

    public Thread genProcess(Disk<Float, GraphNode> disk, GraphNode node, LRUCache<Float, GraphNode> overlap, Callback callback, Class<? extends GraphTraversal> className) {
        LRUCache<Float, GraphNode> cache = new LRUCache<Float, GraphNode>(disk, separate);
        List<LRUCache<Float, GraphNode>> list = new ArrayList<>();
        list.add(cache);
        list.add(overlap);

        Thread thread = new Thread(() -> {
            Process p = new Process(list);
//            GraphTraversal traversal = new BFS(node, p);
            GraphTraversal traversal = null;
            try {
                traversal = className.
                        getConstructor(GraphNode.class, Process.class)
                        .newInstance(node, p);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch(IllegalAccessException e) {
                return;

            } catch(NoSuchMethodException e) {
                //default case
                traversal = new BFS(node, p);
                traversal.traverse();

            } catch(InvocationTargetException ignored) { }

            traversal.traverse();
            callback.callback(p);
        });

        return thread;
    }

    public void runSim(int numNodes, double percentCycle, int maxFan) throws InterruptedException {

        GraphGen gen = new GraphGen(numNodes, percentCycle);
        GraphNode node = gen.genRandGraph(maxFan);
//        GraphNode node2 = gen.genRandGraph(maxFan);
//        gen.createRandConnection(node, node2, 100000);
        GraphNode node2 = null;
        while(node2 == null) {
            node2 = gen.getRandNode(node, 2);
        }

        Disk<Float, GraphNode> disk = DiskGen.genDisk(node);
        LRUCache<Float, GraphNode> overlap = new LRUCache<Float, GraphNode>(disk, shared);

        Thread a = this.genProcess(disk, node, overlap, (process -> {
//            System.out.println("P1 total hits: " + process.getTotalHits());
//            System.out.println("P1 total misses: " + process.getTotalMisses());
            double missRate = (double) process.getTotalMisses() / process.getNumAccess();
            System.out.println("P1 miss rate: " + missRate);


        }), BFS.class);

        Thread b = this.genProcess(disk, node2, overlap, (process -> {
//            System.out.println("P2 total hits: " + process.getTotalHits());
//            System.out.println("P2 total misses: " + process.getTotalMisses());
            double missRate = (double) process.getTotalMisses() / process.getNumAccess();
            System.out.println("P2 miss rate: " + missRate);
        }), DFS.class);

        a.start();
        b.start();

        a.join();
        b.join();

        System.out.println("------------------------------");
    }
}

import Cache.LRUCache;
import Disk.Disk;
import Node.GraphNode;
import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class DiskGen {
    public static Disk<Float, GraphNode> genDisk(GraphNode node) {
        Disk<Float, GraphNode> disk = new Disk<Float, GraphNode>();
        Queue<GraphNode> queue = new LinkedList<>();
        Set<GraphNode> set = new HashSet<>();
        queue.add(node);

        //use bfs to traverse and store all nodes into disk
        while(!queue.isEmpty()) {
            GraphNode n = queue.poll();
            disk.addToDisk(n.getVal(), n);

            for(GraphNode g : n.getChildren()) {
                if(!set.contains(g)) {
                    set.add(g);
                    queue.add(g);
                }
            }
        }
        return disk;
    }
}

package GraphTraversal;

import Node.GraphNode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import Process.Process;

public class BFS extends GraphTraversal{

    public BFS(GraphNode node, Process p) {
        super(node, p);
    }

    public void traverse() {
        GraphNode node = this.getNode();
        Process process = this.getProcess();

        Queue<GraphNode> queue = new LinkedList<>();
        Set<GraphNode> visited = new HashSet<>();
        queue.add(node);

        while(!queue.isEmpty()) {
            GraphNode res = queue.poll();
//            System.out.println(res.getChildren().size());
//            System.out.println(res.getVal());
            process.retrieveFromPartitionedCache(res.getVal());

            for(GraphNode temp : res.getChildren()) {
                if(!visited.contains(temp)) {
                    visited.add(temp);
                    queue.add(temp);
                }
            }
        }
    }
}

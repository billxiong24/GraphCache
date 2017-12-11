package GraphTraversal;

import Node.GraphNode;
import Process.Process;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DFS extends GraphTraversal {

    public DFS(GraphNode node, Process p) {
        super(node, p);
    }

    public void traverse() {
        Process process = this.getProcess();
        GraphNode node = this.getNode();
        Stack<GraphNode> stack = new Stack<>();
        Set<GraphNode> set = new HashSet<>();
        stack.push(node);

        while(!stack.isEmpty()) {
            GraphNode g = stack.pop();
            process.retrieveFromPartitionedCache(g.getVal());
            for(GraphNode child : g.getChildren()) {
                process.retrieveFromPartitionedCache(child.getVal());
                if(!set.contains(child)) {
                    stack.push(child);
                    set.add(child);
                }
            }
        }
    }
}

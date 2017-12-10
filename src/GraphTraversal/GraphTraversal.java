package GraphTraversal;

import Node.GraphNode;
import Process.Process;

public abstract class GraphTraversal {

    private GraphNode node;
    private Process process;
    public GraphTraversal(GraphNode node, Process process) {
        this.process = process;
        this.node = node;
    }

    public Process getProcess() {
        return process;
    }

    GraphNode getNode() {
        return node;
    }

    public abstract void traverse();
}

import Node.GraphNode;

import java.util.*;

public class GraphGen {
    private int numNodes;
    private double percentCycle;
    public GraphGen(int numNodes, double percentCycle) {
        this.numNodes = numNodes;
        this.percentCycle = percentCycle;
    }

    public GraphNode getRandNode(GraphNode node, int maxNodes) {
        int ind = (int) (Math.random() * maxNodes) ;
        Queue<GraphNode> queue = new LinkedList<>();
        Set<GraphNode> visited = new HashSet<>();
        queue.add(node);

        int counter = 0;
        GraphNode res = null;

        while(!queue.isEmpty() && counter < ind) {
            res = queue.poll();
            counter++;
            for(GraphNode child : res.getChildren()) {
                if(!visited.contains(child)) {
                    visited.add(child);
                    queue.add(child);
                }
            }
        }
        return res;
    }

    public GraphNode genRandGraph(int maxChild) {
        GraphNode node = new GraphNode((float) Math.random());
        Queue<GraphNode> queue = new LinkedList<>();
        List<GraphNode> visited = new ArrayList<>();
        queue.add(node);

        int counter = 1;

        while(!queue.isEmpty() && counter < numNodes) {
            GraphNode res = queue.poll();
            int num = (int) (Math.random() * maxChild) + 1;

            for(int i = 0; i < num; i++) {
                counter++;
                GraphNode temp = new GraphNode((float) Math.random());
                double test = Math.random();
                if(test < this.percentCycle && visited.size() > 1) {
                    int ind = (int) (Math.random() * visited.size());
                    temp.addChild(visited.get(ind));
                }
                else {
                }

                res.addChild(temp);

                visited.add(temp);
                queue.add(temp);

                if(counter >= numNodes)
                    return node;
            }
        }
        return node;
    }
}

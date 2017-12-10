import Node.GraphNode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class GraphGen {

    public static GraphNode genRandGraph(int numNodes, int maxChild) {
        GraphNode node = new GraphNode((float) Math.random());
        Queue<GraphNode> queue = new LinkedList<>();
        Set<GraphNode> visited = new HashSet<>();
        queue.add(node);

        int counter = 1;

        while(!queue.isEmpty() && counter < numNodes) {
            GraphNode res = queue.poll();
            int num = (int) (Math.random() * maxChild) + 1;

            for(int i = 0; i < num; i++) {
                counter++;
                GraphNode temp = new GraphNode((float) Math.random());
                res.addChild(temp);
                queue.add(temp);
                if(counter >= numNodes)
                    return node;
            }
        }
        return node;
    }
}

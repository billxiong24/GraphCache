package Simulation;

import Node.GraphNode;
import com.sun.corba.se.impl.orbutil.graph.Graph;

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

    public void createRandConnection(GraphNode a, GraphNode b, int numConnections) {
        List<GraphNode> randA = this.storeNodes(a);
        List<GraphNode> randB = this.storeNodes(b);

        int len = Math.min(numConnections, randA.size());

        for(int i = 0; i < len; i++) {
            GraphNode rand1 = randA.get((int) (Math.random() * randA.size()));
            GraphNode rand2 = randA.get((int) (Math.random() * randB.size()));

            rand1.addChild(rand2);
            rand2.addChild(rand1);
        }
    }
    private List<GraphNode> storeNodes(GraphNode node) {
        List<GraphNode> store = new ArrayList<>();

        Queue<GraphNode> queue = new LinkedList<>();
        Set<GraphNode> visited = new HashSet<>();
        queue.add(node);

        while(!queue.isEmpty()) {
            GraphNode res = queue.poll();
            store.add(res);
            for(GraphNode temp : res.getChildren()) {
                if(!visited.contains(temp)) {
                    visited.add(temp);
                    queue.add(temp);
                }
            }
        }
        return store;
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
                temp.addChild(res);

                visited.add(temp);
                queue.add(temp);

                if(counter >= numNodes)
                    return node;
            }
        }
        return node;
    }
}

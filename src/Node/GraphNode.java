package Node;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GraphNode {
    private Float val;
    private Set<GraphNode> children;

    public GraphNode(Float val) {
        this.val =  val;
        children = new HashSet<>();
    }

    public float getVal() {
        return val;
    }

    public Set<GraphNode> getChildren() {
       return Collections.unmodifiableSet(children);
    }

    public void addChild(GraphNode g) {
        children.add(g);
    }
}

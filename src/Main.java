import Cache.LRUCache;
import Node.GraphNode;
import Process.Process;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String args[]) {
        List<LRUCache<Float, Float>> list = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            list.add(new LRUCache<>(3));
        }

        Process process = new Process(list);
        for(int i = 0; i < 30; i++) {

            process.addToPartitionedCache((float) Math.random(), (float) Math.random());
        }
    }

}

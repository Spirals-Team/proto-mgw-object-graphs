package proto;

import org.junit.Test;
import org.mwg.Graph;
import org.mwg.Node;

public class Spec {

    @Test
    public void simpleTest() {
        Graph graph = MwgDB.graph();



        Node n = graph.newNode(0,0);
        n.set("name","hello");

        System.out.println(n);

    }

}

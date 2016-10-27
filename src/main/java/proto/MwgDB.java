package proto;

import org.mwg.Graph;
import org.mwg.GraphBuilder;

public class MwgDB {

    private static Graph _graph;

    public synchronized static Graph graph() {
        if (_graph == null) {
            _graph = new GraphBuilder()
                    .withMemorySize(10_000)
                    .build();
        }
        return _graph;
    }

}

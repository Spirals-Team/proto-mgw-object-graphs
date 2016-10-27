package proto;

import org.mwg.Graph;
import org.mwg.GraphBuilder;
import org.mwg.Node;
import org.mwg.Type;
import org.mwg.core.scheduler.NoopScheduler;

import java.lang.reflect.Field;

public class MwgDB {

    private static Graph _graph;

    private static final int world = 0;

    public synchronized static Graph graph() {
        if (_graph == null) {
            _graph = new GraphBuilder()
                    .withMemorySize(10_000)
                    .withScheduler(new NoopScheduler())
                    .build();
            _graph.connect(null);
        }
        return _graph;
    }

    public synchronized static void newNode(Object target) {
        Graph g = graph();
        Node n = g.newNode(0, System.currentTimeMillis());
        //TODO change by addr using unsafe
        n.setProperty("addr", Type.LONG, target.hashCode());
        g.index(target.getClass().getCanonicalName(), n, "addr", null);
    }

    public synchronized static void updateNode(Object target) {
        Graph g = graph();
        g.find(0, System.currentTimeMillis(), target.getClass().getCanonicalName(), "addr=" + target.hashCode(), toUpdateNodes -> {
            final Node toUpdateNode = toUpdateNodes[0];
            //TODO
            for (Field f : target.getClass().getFields()) {
                if (f.getType().equals(String.class)) {
                    String fieldValue;
                    try {
                        fieldValue = (String) f.get(target);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    toUpdateNode.setProperty(f.getName(), Type.STRING, fieldValue);
                }
            }
        });
    }

}

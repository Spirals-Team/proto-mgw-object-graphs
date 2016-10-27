package proto;

import org.junit.Assert;
import org.junit.Test;
import org.mwg.Constants;
import org.mwg.Graph;
import org.mwg.Node;

public class Spec {

    @Test
    public void simpleTest() throws Exception {
        Graph graph = MwgDB.graph();
        IFoo foo = Spoonify.instrument();
        Assert.assertNotNull(foo);
        graph.findAll(0, System.currentTimeMillis(), "proto.Foo", result -> {
            Assert.assertEquals(1, result.length);
            Assert.assertTrue(((long) result[0].get("addr")) == foo.hashCode());
            System.out.println(result.length);
        });
        Thread.sleep(20);
        foo.setName("first");
        Thread.sleep(20);
        foo.setName("second");

        graph.find(0, System.currentTimeMillis(), foo.getClass().getCanonicalName(), "addr=" + foo.hashCode(), toUpdateNodes -> {
            final Node toUpdateNode = toUpdateNodes[0];
            toUpdateNode.timepoints(Constants.BEGINNING_OF_TIME,Constants.END_OF_TIME, times -> {
                Assert.assertEquals(3,times.length);
                Assert.assertEquals("second",toUpdateNode.get("name"));

                graph.lookup(0,times[times.length-1],toUpdateNode.id(), result -> {
                    //the first one has no name because no setter has been called already
                    Assert.assertNull(result.get("name"));
                });

            });

        });

    }

}

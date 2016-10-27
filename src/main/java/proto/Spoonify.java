package proto;

import spoon.Launcher;
import spoon.reflect.code.CtBlock;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

public class Spoonify {

    public static IFoo instrument() throws Exception {
        Launcher spoon = new Launcher();
        spoon.addInputResource("src/main/tjava/Foo.java");
        spoon.buildModel();
        CtClass<?> klass = spoon.getFactory().Class().get("proto.Foo");
        CtBlock constructor = klass.getConstructor().getBody();
        constructor.addStatement(spoon.getFactory().Code().createCodeSnippetStatement("proto.MwgDB.newNode(this)"));

        for (CtMethod ct : klass.getMethods()) {
            CtMethod method = ct;
            if (method.getSimpleName().startsWith("set")) {
                method.getBody().insertEnd(spoon.getFactory().Code().createCodeSnippetStatement("proto.MwgDB.updateNode(this)"));
            }
        }

        //ferme les yeux !
        return (IFoo) klass.newInstance();

        //return (IFoo) klass
    }

}

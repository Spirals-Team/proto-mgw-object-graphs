package proto;

public class Foo implements IFoo {
    public IFoo ref;
    public String name;

    public void setReference(IFoo x) {
        ref = x;
    }

    public void setName(String n) {
        name = n;
    }

    public Foo() {

    }
}

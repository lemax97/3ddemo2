package demo3D;

public class Demo3D extends BaseGame {
    @Override
    public void create() {
        setScreen(new DemoScreen(this));
    }
}

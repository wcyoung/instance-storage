package wcyoung.storage.instance.classes;

public class InfiniteLoop {

    private InfiniteLoop loop;

    public InfiniteLoop(InfiniteLoop loop) {
        this.loop = loop;
    }

    public InfiniteLoop getLoop() {
        return loop;
    }

}

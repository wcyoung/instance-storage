package wcyoung.storage.instance.collector;

public class DependencyCollectException extends RuntimeException {

    private static final long serialVersionUID = 2293105689505297743L;

    public DependencyCollectException() {
        super();
    }

    public DependencyCollectException(String message) {
        super(message);
    }

    public DependencyCollectException(String message, Throwable cause) {
        super(message, cause);
    }

    public DependencyCollectException(Throwable cause) {
        super(cause);
    }

}

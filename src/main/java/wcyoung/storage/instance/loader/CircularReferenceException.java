package wcyoung.storage.instance.loader;

public class CircularReferenceException extends RuntimeException {

    private static final long serialVersionUID = -8618395681651340091L;

    public CircularReferenceException() {
        super();
    }

    public CircularReferenceException(String message) {
        super(message);
    }

    public CircularReferenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CircularReferenceException(Throwable cause) {
        super(cause);
    }

}

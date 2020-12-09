package wcyoung.storage.instance.loader;

public class InstanceLoadException extends RuntimeException {

    public InstanceLoadException() {
        super();
    }

    public InstanceLoadException(String message) {
        super(message);
    }

    public InstanceLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceLoadException(Throwable cause) {
        super(cause);
    }

}

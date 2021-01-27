package wcyoung.storage.instance.generator;

public class InstanceGenerateException extends RuntimeException {

    private static final long serialVersionUID = 4339245518434536323L;

    public InstanceGenerateException() {
        super();
    }

    public InstanceGenerateException(String message) {
        super(message);
    }

    public InstanceGenerateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceGenerateException(Throwable cause) {
        super(cause);
    }

}

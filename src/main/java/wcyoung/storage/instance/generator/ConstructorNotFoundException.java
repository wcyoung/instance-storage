package wcyoung.storage.instance.generator;

public class ConstructorNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 4013470905039289683L;

    public ConstructorNotFoundException() {
        super();
    }

    public ConstructorNotFoundException(String message) {
        super(message);
    }

    public ConstructorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstructorNotFoundException(Throwable cause) {
        super(cause);
    }

}

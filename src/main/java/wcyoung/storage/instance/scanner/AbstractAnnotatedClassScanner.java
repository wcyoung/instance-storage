package wcyoung.storage.instance.scanner;

import java.lang.annotation.Annotation;

public abstract class AbstractAnnotatedClassScanner<T> implements ClassScanner<T> {

    protected String basePackage;
    protected Class<? extends Annotation> annotationType;

    public AbstractAnnotatedClassScanner(String basePackage, Class<? extends Annotation> annotationType) {
        this.basePackage = basePackage;
        this.annotationType = annotationType;
    }

}

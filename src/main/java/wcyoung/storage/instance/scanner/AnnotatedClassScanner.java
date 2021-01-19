package wcyoung.storage.instance.scanner;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.util.Set;

public class AnnotatedClassScanner extends AbstractAnnotatedClassScanner<Set<Class<?>>> {

    public AnnotatedClassScanner(String basePackage, Class<? extends Annotation> annotationType) {
        super(basePackage, annotationType);
    }

    @Override
    public Set<Class<?>> scan() {
        Reflections reflections = new Reflections(basePackage, new TypeAnnotationsScanner());
        return reflections.getTypesAnnotatedWith(annotationType, true);
    }

}

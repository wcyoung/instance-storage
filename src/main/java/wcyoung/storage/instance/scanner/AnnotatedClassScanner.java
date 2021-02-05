package wcyoung.storage.instance.scanner;

import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.util.Set;

public class AnnotatedClassScanner extends AbstractAnnotatedClassScanner<Set<Class<?>>> {

    public AnnotatedClassScanner(String basePackage, Class<? extends Annotation> annotationType) {
        super(basePackage, annotationType);
    }

    @Override
    public Set<Class<?>> scan() {
        if (basePackage == null || annotationType == null) {
            return null;
        }

        Reflections reflections = new Reflections(basePackage, new TypeAnnotationsScanner());
        try {
            return reflections.getTypesAnnotatedWith(annotationType, true);
        } catch (ReflectionsException e) {
            return null;
        }
    }

}

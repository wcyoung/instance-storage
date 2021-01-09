package wcyoung.storage.instance.loader;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.util.Set;

public class AnnotatedClassScanner {

    public static Class<?>[] scan(String basePackage, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(basePackage, new TypeAnnotationsScanner());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(annotation, true);
        return classes.stream().toArray(Class<?>[]::new);
    }

}

package wcyoung.storage.instance.loader;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class AnnotatedClassScanner {

    public static Class<?>[] scan(String basePackage, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(annotation);
        return classes.stream().toArray(Class<?>[]::new);
    }

}

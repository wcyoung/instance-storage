package wcyoung.storage.instance.scanner;

import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.reflections.scanners.TypeAnnotationsScanner;
import wcyoung.storage.instance.annotation.Item;

import java.util.Set;

public class ItemScanner extends AbstractAnnotatedClassScanner<Set<Class<?>>> {

    public ItemScanner(String basePackage) {
        super(basePackage, Item.class);
    }

    @Override
    public Set<Class<?>> scan() {
        if (basePackage == null) {
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

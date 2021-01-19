package wcyoung.storage.instance.scanner;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import wcyoung.storage.instance.annotation.Item;

import java.util.Set;

public class ItemScanner extends AbstractAnnotatedClassScanner<Set<Class<?>>> {

    public ItemScanner(String basePackage) {
        super(basePackage, Item.class);
    }

    @Override
    public Set<Class<?>> scan() {
        Reflections reflections = new Reflections(basePackage, new TypeAnnotationsScanner());
        return reflections.getTypesAnnotatedWith(annotationType, true);
    }

}

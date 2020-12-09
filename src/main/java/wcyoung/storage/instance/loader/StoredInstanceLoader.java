package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.Storage;
import wcyoung.storage.instance.Stored;

public class StoredInstanceLoader implements InstanceLoader {

    private Storage storage;

    public StoredInstanceLoader() {
        storage = storage.getInstance();
    }

    @Override
    public void load(String basePackage) {
        Class<?>[] classes = ClassWithAnnotationScanner.scan(basePackage, Stored.class);

        for (Class<?> clazz : classes) {
            try {
                storage.add(clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new InstanceLoadException(clazz + " does not have a public constructor.");
            }
        }
    }

}

package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.Storage;
import wcyoung.storage.instance.generator.InstanceGenerator;

import java.util.Set;

public class ClassesInstanceLoader implements InstanceLoader {

    private Storage<Class<?>, Object> storage;
    private Set<Class<?>> classes;

    public ClassesInstanceLoader(Storage<Class<?>, Object> storage, Set<Class<?>> classes) {
        this.storage = storage;
        this.classes = classes;
    }

    @Override
    public boolean load() {
        if (storage == null || classes == null) {
            return false;
        }

        for (Class<?> clazz : classes) {
            Object instance = InstanceGenerator.generate(clazz);
            storage.add(clazz, instance);
        }

        return true;
    }

}

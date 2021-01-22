package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.Storage;
import wcyoung.storage.instance.generator.InstanceGenerator;

import java.util.Set;

public class ClassesInstanceLoader implements InstanceLoader<Class<?>, Object> {

    private Set<Class<?>> classes;

    public ClassesInstanceLoader(Set<Class<?>> classes) {
        this.classes = classes;
    }

    @Override
    public boolean load(Storage<Class<?>, Object> storage) {
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

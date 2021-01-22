package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.Storage;
import wcyoung.storage.instance.generator.InstanceGenerator;

import java.util.Map;
import java.util.Map.Entry;

public class MappedClassesInstanceLoader implements InstanceLoader<Class<?>, Object> {

    private Map<Class<?>, Class<?>> classes;

    public MappedClassesInstanceLoader(Map<Class<?>, Class<?>> classes) {
        this.classes = classes;
    }

    @Override
    public boolean load(Storage<Class<?>, Object> storage) {
        if (storage == null || classes == null) {
            return false;
        }

        for (Entry<Class<?>, Class<?>> entry : classes.entrySet()) {
            Object instance = InstanceGenerator.generate(entry.getValue());
            storage.add(entry.getKey(), instance);
        }

        return true;
    }

}

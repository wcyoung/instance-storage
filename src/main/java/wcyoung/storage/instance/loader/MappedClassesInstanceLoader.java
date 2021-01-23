package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.Storage;
import wcyoung.storage.instance.generator.InstanceGenerator;

import java.util.Map;
import java.util.Map.Entry;

public class MappedClassesInstanceLoader extends AbstractInstanceLoader<Class<?>, Object> {

    private Map<Class<?>, Class<?>> classes;

    public MappedClassesInstanceLoader(Storage<Class<?>, Object> storage, Map<Class<?>, Class<?>> classes) {
        super(storage);
        this.classes = classes;
    }

    @Override
    public boolean load() {
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

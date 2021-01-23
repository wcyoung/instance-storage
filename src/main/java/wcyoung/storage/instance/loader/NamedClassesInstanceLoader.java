package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.Storage;
import wcyoung.storage.instance.generator.InstanceGenerator;

import java.util.Map;
import java.util.Map.Entry;

public class NamedClassesInstanceLoader extends AbstractInstanceLoader<String, Object> {

    private Map<String, Class<?>> classes;

    public NamedClassesInstanceLoader(Storage<String, Object> storage, Map<String, Class<?>> classes) {
        super(storage);
        this.classes = classes;
    }

    @Override
    public boolean load() {
        if (storage == null || classes == null) {
            return false;
        }

        for (Entry<String, Class<?>> entry : classes.entrySet()) {
            Object instance = InstanceGenerator.generate(entry.getValue());
            storage.add(entry.getKey(), instance);
        }

        return true;
    }

}

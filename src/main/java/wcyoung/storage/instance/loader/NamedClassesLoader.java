package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.Storage;
import wcyoung.storage.instance.generator.InstanceGenerator;
import wcyoung.storage.instance.scanner.ClassScanner;

import java.util.Map;

public class NamedClassesLoader extends AbstractStorageLoader<String, Object> {

    private Map<String, Class<?>> classes;
    private ClassScanner<Map<String, Class<?>>> scanner;

    public NamedClassesLoader(Storage<String, Object> storage, Map<String, Class<?>> classes) {
        super(storage);
        this.classes = classes;
    }

    public NamedClassesLoader(Storage<String, Object> storage, ClassScanner<Map<String, Class<?>>> scanner) {
        super(storage);
        this.scanner = scanner;
    }

    @Override
    public boolean load() {
        if (storage == null) {
            return false;
        }

        if (scanner != null) {
            classes = scanner.scan();
        }

        if (classes == null) {
            return false;
        }

        for (Map.Entry<String, Class<?>> entry : classes.entrySet()) {
            Object instance = InstanceGenerator.generate(entry.getValue());
            storage.add(entry.getKey(), instance);
        }

        return true;
    }

}

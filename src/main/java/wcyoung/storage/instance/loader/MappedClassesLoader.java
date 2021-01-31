package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.Storage;
import wcyoung.storage.instance.generator.InstanceGenerator;
import wcyoung.storage.instance.scanner.ClassScanner;

import java.util.Map;

public class MappedClassesLoader extends AbstractStorageLoader<Class<?>, Object> {

    private Map<Class<?>, Class<?>> classes;
    private ClassScanner<Map<Class<?>, Class<?>>> scanner;

    public MappedClassesLoader(Storage<Class<?>, Object> storage, Map<Class<?>, Class<?>> classes) {
        super(storage);
        this.classes = classes;
    }

    public MappedClassesLoader(Storage<Class<?>, Object> storage, ClassScanner<Map<Class<?>, Class<?>>> scanner) {
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

        for (Map.Entry<Class<?>, Class<?>> entry : classes.entrySet()) {
            Object instance = InstanceGenerator.generate(entry.getValue());
            storage.add(entry.getKey(), instance);
        }

        return true;
    }

}

package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.Storage;
import wcyoung.storage.instance.generator.InstanceGenerator;
import wcyoung.storage.instance.scanner.ClassScanner;

import java.util.Set;

public class ClassesLoader extends AbstractStorageLoader<Class<?>, Object> {

    private Set<Class<?>> classes;
    private ClassScanner<Set<Class<?>>> scanner;

    public ClassesLoader(Storage<Class<?>, Object> storage, Set<Class<?>> classes) {
        super(storage);
        this.classes = classes;
    }

    public ClassesLoader(Storage<Class<?>, Object> storage, ClassScanner<Set<Class<?>>> scanner) {
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

        for (Class<?> clazz : classes) {
            Object instance = InstanceGenerator.generate(clazz);
            storage.add(clazz, instance);
        }

        return true;
    }

}

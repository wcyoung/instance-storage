package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.Storage;
import wcyoung.storage.instance.generator.InstanceGenerator;
import wcyoung.storage.instance.scanner.ClassScanner;

import java.util.Set;

public class ScannedClassesInstanceLoader implements InstanceLoader {

    private Storage<Class<?>, Object> storage;
    private ClassScanner<Set<Class<?>>> scanner;

    public ScannedClassesInstanceLoader(Storage<Class<?>, Object> storage, ClassScanner<Set<Class<?>>> scanner) {
        this.storage = storage;
        this.scanner = scanner;
    }

    @Override
    public boolean load() {
        if (storage == null || scanner == null) {
            return false;
        }

        Set<Class<?>> classes = scanner.scan();
        for (Class<?> clazz : classes) {
            Object instance = InstanceGenerator.generate(clazz);
            storage.add(clazz, instance);
        }

        return true;
    }

}

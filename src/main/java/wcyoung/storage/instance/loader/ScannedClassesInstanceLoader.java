package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.Storage;
import wcyoung.storage.instance.generator.InstanceGenerator;
import wcyoung.storage.instance.scanner.ClassScanner;

import java.util.Set;

public class ScannedClassesInstanceLoader implements InstanceLoader<Class<?>, Object> {

    private ClassScanner<Set<Class<?>>> scanner;

    public ScannedClassesInstanceLoader(ClassScanner<Set<Class<?>>> scanner) {
        this.scanner = scanner;
    }

    @Override
    public boolean load(Storage<Class<?>, Object> storage) {
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

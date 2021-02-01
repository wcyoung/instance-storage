package wcyoung.storage.instance.collector;

import wcyoung.storage.instance.generator.InstanceGenerator;
import wcyoung.storage.instance.scanner.ClassScanner;

import java.lang.reflect.Constructor;
import java.util.*;

public class ClassesDependencyCollector implements DependencyCollector<Map<Class<?>, List<Class<?>>>> {

    private Set<Class<?>> classes;
    private ClassScanner<Set<Class<?>>> scanner;

    public ClassesDependencyCollector(Set<Class<?>> classes) {
        this.classes = classes;
    }

    public ClassesDependencyCollector(ClassScanner<Set<Class<?>>> scanner) {
        this.scanner = scanner;
    }

    @Override
    public Map<Class<?>, List<Class<?>>> collect() {
        if (scanner != null) {
            classes = scanner.scan();
        }

        if (classes == null) {
            return null;
        }

        Map<Class<?>, List<Class<?>>> dependencies = new HashMap<>();

        classes.forEach(clazz -> {
            List<Class<?>> parameterTypes = new ArrayList<>();

            Constructor<?> constructor = InstanceGenerator.findConstructor(clazz);
            for (Class<?> parameterType : constructor.getParameterTypes()) {
                if (parameterTypes.contains(parameterType)) {
                    throw new DependencyCollectException(
                            "Duplicate dependencies exist. " + clazz + " -> " + parameterType);
                }

                parameterTypes.add(parameterType);
            }

            dependencies.put(clazz, parameterTypes);
        });

        return dependencies;
    }

}

package wcyoung.storage.instance.scanner;

import wcyoung.storage.instance.generator.InstanceGenerator;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DependencyCollector implements ClassCollector<Map<Class<?>, Set<Class<?>>>> {

    private Set<Class<?>> classes;

    public DependencyCollector(Set<Class<?>> classes) {
        this.classes = classes;
    }

    @Override
    public Map<Class<?>, Set<Class<?>>> collect() {
        Map<Class<?>, Set<Class<?>>> collectedClasses = new HashMap<>();
        classes.stream().forEach(clazz -> {
            Set<Class<?>> parameterTypes = new HashSet<>();

            Constructor<?> constructor = InstanceGenerator.findConstructor(clazz);
            for (Class<?> parameterType : constructor.getParameterTypes()) {
                parameterTypes.add(parameterType);
            }

            collectedClasses.put(clazz, parameterTypes);
        });

        return collectedClasses;
    }

}

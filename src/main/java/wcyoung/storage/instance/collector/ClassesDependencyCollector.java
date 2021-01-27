package wcyoung.storage.instance.collector;

import wcyoung.storage.instance.generator.InstanceGenerator;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClassesDependencyCollector implements DependencyCollector<Map<Class<?>, Set<Class<?>>>> {

    private Set<Class<?>> classes;

    public ClassesDependencyCollector(Set<Class<?>> classes) {
        this.classes = classes;
    }

    @Override
    public Map<Class<?>, Set<Class<?>>> collect() {
        Map<Class<?>, Set<Class<?>>> dependencies = new HashMap<>();
        classes.stream().forEach(clazz -> {
            Set<Class<?>> parameterTypes = new HashSet<>();

            Constructor<?> constructor = InstanceGenerator.findConstructor(clazz);
            for (Class<?> parameterType : constructor.getParameterTypes()) {
                parameterTypes.add(parameterType);
            }

            dependencies.put(clazz, parameterTypes);
        });

        return dependencies;
    }

}

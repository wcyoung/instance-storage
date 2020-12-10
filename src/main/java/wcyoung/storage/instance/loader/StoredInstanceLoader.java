package wcyoung.storage.instance.loader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import wcyoung.storage.instance.ForceInject;
import wcyoung.storage.instance.Inject;
import wcyoung.storage.instance.Storage;
import wcyoung.storage.instance.Stored;

public class StoredInstanceLoader implements InstanceLoader {

    private final Storage STORAGE;

    public StoredInstanceLoader() {
        STORAGE = Storage.getInstance();
    }

    @Override
    public void load(String basePackage) {
        Class<?>[] classes = AnnotatedClassScanner.scan(basePackage, Stored.class);

        Map<Class<?>, Set<Class<?>>> lazyInjectClasses = new HashMap<>();

        for (Class<?> clazz : classes) {
            STORAGE.add(generateInstance(clazz, lazyInjectClasses));
        }

        for (Entry<Class<?>, Set<Class<?>>> entry : lazyInjectClasses.entrySet()) {
            inject(entry.getKey(), entry.getValue());
        }
    }

    protected Object generateInstance(Class<?> clazz, Map<Class<?>, Set<Class<?>>> lazyInjectClasses) {
        Constructor<?> constructor = findConstructor(clazz);

        Set<Class<?>> classesToInject = new HashSet<>();

        Object[] parameters = Stream.of(constructor.getParameterTypes())
                .map(type -> {
                    if (!type.isAnnotationPresent(Stored.class)) {
                        return newInstance(type);
                    }

                    if (STORAGE.has(type)) {
                        return STORAGE.get(type);
                    }

                    classesToInject.add(type);
                    return null;
                })
                .toArray();

        if (!classesToInject.isEmpty()) {
            lazyInjectClasses.put(clazz, classesToInject);
        }

        return newInstance(constructor, parameters);
    }

    protected Object newInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InstanceLoadException(
                    clazz + " Parameters of constructor must have a default public constructor.");
        }
    }

    protected Object newInstance(Constructor<?> constructor, Object[] parameters) {
        try {
            return constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new InstanceLoadException(e);
        }
    }

    protected Constructor<?> findConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors.length == 0) {
            throw new InstanceLoadException(clazz + " does not have a public constructor.");
        }

        if (constructors.length == 1) {
            return constructors[0];
        }

        Constructor<?>[] constructorsWithInject = Stream.of(constructors)
                .filter(constructor -> constructor.isAnnotationPresent(Inject.class))
                .toArray(Constructor<?>[]::new);

        if (constructorsWithInject.length == 0) {
            throw new InstanceLoadException(clazz + " does not have a public constructor with @Inject");
        }

        if (constructorsWithInject.length > 1) {
            throw new InstanceLoadException(clazz + " has too many public constructors with @Inject");
        }

        return constructorsWithInject[0];
    }

    protected void inject(Class<?> clazz, Set<Class<?>> fieldTypes) {
        Stream.of(clazz.getDeclaredFields())
                .filter(f -> fieldTypes.contains(f.getType())
                        && f.isAnnotationPresent(ForceInject.class))
                .forEach(f -> {
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }

                    try {
                        f.set(STORAGE.get(clazz), STORAGE.get(f.getType()));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        throw new InstanceLoadException(e);
                    }
                });
    }

}

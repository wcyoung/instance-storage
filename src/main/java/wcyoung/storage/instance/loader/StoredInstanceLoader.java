package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.ForceInject;
import wcyoung.storage.instance.Inject;
import wcyoung.storage.instance.Storage;
import wcyoung.storage.instance.Stored;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.Map.Entry;

public class StoredInstanceLoader implements InstanceLoader {

    private final Storage STORAGE;

    public StoredInstanceLoader() {
        STORAGE = Storage.getInstance();
    }

    protected Class<?>[] scan(String basePackage) {
        return AnnotatedClassScanner.scan(basePackage, Stored.class);
    }

    @Override
    public void load(String basePackage) {
        Map<Class<?>, Set<Class<?>>> lazyInjectClasses = new HashMap<>();

        Class<?>[] classes = scan(basePackage);
        for (Class<?> clazz : classes) {
            loadInstances(clazz, lazyInjectClasses);
        }

        for (Entry<Class<?>, Set<Class<?>>> entry : lazyInjectClasses.entrySet()) {
            inject(entry.getKey(), entry.getValue());
        }
    }

    protected Object loadInstances(Class<?> clazz, Map<Class<?>, Set<Class<?>>> lazyInjectClasses) {
        Storage storage = Storage.getInstance();
        if (storage.has(clazz)) {
            return storage.get(clazz);
        }

        Set<Class<?>> classesToInject = new HashSet<>();

        Constructor<?> constructor = findConstructor(clazz);
        Object[] parameters = Arrays.stream(constructor.getParameterTypes())
                .map(type -> {
                    if (!type.isAnnotationPresent(Stored.class)) {
                        return null;
                    }

                    // circular reference
                    if (hasConstructorParameterType(type, clazz)) {
                        if (!hasForceInjectField(clazz, type)) {
                            throw new InstanceLoadException("circular reference error. cause without @ForceInject");
                        }

                        classesToInject.add(type);
                        return null;
                    }

                    /*
                    if (STORAGE.has(type)) {
                        return STORAGE.get(type);
                    }
                    */

                    return loadInstances(type, lazyInjectClasses);
                })
                .toArray();

        if (!classesToInject.isEmpty()) {
            lazyInjectClasses.put(clazz, classesToInject);
        }

        Object instance = newInstance(constructor, parameters);
        storage.add(clazz, instance);

        return instance;
    }

    protected boolean hasConstructorParameterType(Class<?> clazz, Class<?> parameterType) {
        Constructor<?> constructor = findConstructor(clazz);

        for (Class<?> type : constructor.getParameterTypes()) {
            if (type == parameterType) {
                return true;
            }
        }

        return false;
    }

    protected boolean hasForceInjectField(Class<?> clazz, Class<?> fieldType) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType() == fieldType && field.isAnnotationPresent(ForceInject.class)) {
                return true;
            }
        }

        return false;
    }

    protected Constructor<?> findConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors.length == 0) {
            throw new InstanceLoadException(clazz + " does not have a public constructor.");
        }

        if (constructors.length == 1) {
            return constructors[0];
        }

        Constructor<?>[] constructorsWithInject = Arrays.stream(constructors)
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

    protected void inject(Class<?> clazz, Set<Class<?>> fieldTypes) {
        Arrays.stream(clazz.getDeclaredFields())
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

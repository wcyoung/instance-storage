package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.Storage;
import wcyoung.storage.instance.Stored;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public class StoredInstanceLoader implements InstanceLoader {

    private Storage storage;

    public StoredInstanceLoader() {
        storage = Storage.getInstance();
    }

    @Override
    public void load(String basePackage) {
        Class<?>[] classes = AnnotatedClassScanner.scan(basePackage, Stored.class);

        for (Class<?> clazz : classes) {
            storage.add(generateInstance(clazz));
        }
    }

    protected Object generateInstance(Class<?> clazz) {
        Constructor<?> constructor = findConstructor(clazz);

        Object[] parameters = Stream.of(constructor.getParameterTypes())
                .map(type -> {
                    try {
                        return type.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new InstanceLoadException(
                                "Parameters of constructor must have a default public constructor.");
                    }
                })
                .toArray();

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

        if (constructors.length > 1) {
            throw new InstanceLoadException(clazz + " has too many public constructors.");
        }

        return constructors[0];
    }

}

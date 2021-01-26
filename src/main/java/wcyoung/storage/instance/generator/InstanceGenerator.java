package wcyoung.storage.instance.generator;

import wcyoung.storage.instance.annotation.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class InstanceGenerator {

    public static <T> T generate(Class<T> clazz) {
        try {
            int modifiers = clazz.getModifiers();
            if (Modifier.isInterface(modifiers)) {
                throw new InstanceGenerateException("Unable to generate " + clazz + " instance. Because it is an interface.");
            }

            if (Modifier.isAbstract(modifiers)) {
                throw new InstanceGenerateException("Unable to generate " + clazz + " instance. Because it is an abstract class.");
            }

            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InstanceGenerateException(
                    clazz + " parameters of constructor must have a default public constructor.");
        }
    }

    public static Constructor<?> findConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors.length == 0) {
            throw new ConstructorNotFoundException(clazz + " does not have a public constructor.");
        }

        if (constructors.length == 1) {
            return constructors[0];
        }

        Constructor<?>[] constructorsWithInject = Arrays.stream(constructors)
                .filter(constructor -> constructor.isAnnotationPresent(Inject.class))
                .toArray(Constructor<?>[]::new);

        if (constructorsWithInject.length == 0) {
            throw new ConstructorNotFoundException(clazz + " does not have a public constructor with @Inject");
        }

        if (constructorsWithInject.length > 1) {
            throw new ConstructorNotFoundException(clazz + " has too many public constructors with @Inject");
        }

        return constructorsWithInject[0];
    }

}

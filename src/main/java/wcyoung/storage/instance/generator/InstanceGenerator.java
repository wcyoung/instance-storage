package wcyoung.storage.instance.generator;

import java.lang.reflect.Modifier;

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

}

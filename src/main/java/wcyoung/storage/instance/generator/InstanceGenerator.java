package wcyoung.storage.instance.generator;

public class InstanceGenerator {

    public static <T> T generate(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new InstanceGenerateException(
                    clazz + " parameters of constructor must have a default public constructor.");
        }
    }

}

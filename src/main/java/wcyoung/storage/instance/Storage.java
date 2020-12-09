package wcyoung.storage.instance;

import wcyoung.storage.instance.loader.InstanceLoader;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Storage {

    private static final Storage STORAGE = new Storage();

    private final ConcurrentMap<Class<?>, Object> INSTANCES;

    private Storage() {
        INSTANCES = new ConcurrentHashMap<>();
    }

    public static Storage getInstance() {
        return STORAGE;
    }

    public boolean contains(Class<?> key) {
        return INSTANCES.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> key) {
        return (T) INSTANCES.get(key);
    }

    public void add(Object value) {
        add(value.getClass(), value);
    }

    public void add(Class<?> key, Object value) {
        INSTANCES.put(key, value);
    }

    public void remove(Class<?> key) {
        INSTANCES.remove(key);
    }

    public void load(InstanceLoader loader, String basePackage) {
        loader.load(basePackage);
    }

}

package wcyoung.storage.instance;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Storage {

    private final ConcurrentMap<Class<?>, Object> INSTANCES;

    public Storage() {
        INSTANCES = new ConcurrentHashMap<>();
    }

    public boolean has(Class<?> key) {
        return INSTANCES.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> key) {
        return (T) INSTANCES.get(key);
    }

    public int size() {
        return INSTANCES.size();
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

    public void clear() {
        INSTANCES.clear();
    }

}

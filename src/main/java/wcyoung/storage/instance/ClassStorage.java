package wcyoung.storage.instance;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

public class ClassStorage implements Storage<Class<?>, Object> {

    private final ConcurrentMap<Class<?>, Object> INSTANCES;

    public ClassStorage() {
        INSTANCES = new ConcurrentHashMap<>();
    }

    @Override
    public boolean has(Class<?> key) {
        return INSTANCES.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Class<?> key) {
        return (T) INSTANCES.get(key);
    }

    @Override
    public int size() {
        return INSTANCES.size();
    }

    public boolean add(Object value) {
        return add(value.getClass(), value);
    }

    @Override
    public boolean add(Class<?> key, Object value) {
        if (has(key)) {
            return false;
        }

        INSTANCES.put(key, value);
        return true;
    }

    public <T> boolean add(Supplier<T> supplier) {
        return add(supplier.get());
    }

    @Override
    public <T> boolean add(Class<?> key, Supplier<T> supplier) {
        return add(key, supplier.get());
    }

    @Override
    public boolean replace(Class<?> key, Object value) {
        if (!remove(key)) {
            return false;
        }

        return add(key, value);
    }

    @Override
    public boolean remove(Class<?> key) {
        if (!has(key)) {
            return false;
        }

        INSTANCES.remove(key);
        return true;
    }

    @Override
    public void clear() {
        INSTANCES.clear();
    }

}

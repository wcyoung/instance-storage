package wcyoung.storage.instance;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

public class NameStorage implements Storage<String, Object> {

    private final ConcurrentMap<String, Object> INSTANCES;

    public NameStorage() {
        INSTANCES = new ConcurrentHashMap<>();
    }

    public NameStorage(NameStorage storage) {
        INSTANCES = new ConcurrentHashMap<>(storage.INSTANCES);
    }

    @Override
    public boolean has(String key) {
        return INSTANCES.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key) {
        return (T) INSTANCES.get(key);
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        return get(key);
    }

    @Override
    public Set<String> keys() {
        return new HashSet<>(INSTANCES.keySet());
    }

    @Override
    public int size() {
        return INSTANCES.size();
    }

    public boolean add(Object value) {
        return add(toCamelCase(value.getClass().getSimpleName()), value);
    }

    @Override
    public boolean add(String key, Object value) {
        if (has(key)) {
            return false;
        }

        INSTANCES.put(key, value);
        return true;
    }

    public boolean add(Supplier<?> supplier) {
        return add(supplier.get());
    }

    @Override
    public boolean add(String key, Supplier<?> supplier) {
        return add(key, supplier.get());
    }

    @Override
    public boolean replace(String key, Object value) {
        if (!remove(key)) {
            return false;
        }

        return add(key, value);
    }

    @Override
    public boolean replace(String key, Supplier<?> supplier) {
        return replace(key, supplier.get());
    }

    public boolean merge(Storage<String, Object> storage) {
        return merge(storage, false);
    }

    @Override
    public boolean merge(Storage<String, Object> storage, boolean doOverride) {
        if (storage == null) {
            return false;
        }

        for (String key : storage.keys()) {
            Object newInstance = storage.get(key);
            if (newInstance == null) {
                continue;
            }

            Object ownInstance = get(key);
            if (ownInstance == null) {
                add(key, newInstance);
                continue;
            }

            if (doOverride) {
                replace(key, newInstance);
            }
        }

        return true;
    }

    @Override
    public boolean remove(String key) {
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

    private String toCamelCase(String pascalCase) {
        int length = pascalCase.length();
        if (length < 1) {
            return pascalCase;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(pascalCase.substring(0, 1).toLowerCase());

        if (length > 1) {
            sb.append(pascalCase.substring(1));
        }

        return sb.toString();
    }

}

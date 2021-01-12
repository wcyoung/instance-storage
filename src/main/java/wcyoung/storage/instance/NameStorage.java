package wcyoung.storage.instance;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class NameStorage implements Storage<String, Object> {

    private final ConcurrentMap<String, Object> INSTANCES;

    public NameStorage() {
        INSTANCES = new ConcurrentHashMap<>();
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

    @Override
    public boolean replace(String key, Object value) {
        if (!remove(key)) {
            return false;
        }

        return add(key, value);
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

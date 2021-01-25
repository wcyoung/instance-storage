package wcyoung.storage.instance;

import java.util.Set;
import java.util.function.Supplier;

public interface Storage<K, V> {

    boolean has(K key);

    <T> T get(K key);

    <T> T get(K key, Class<T> type);

    Set<K> keys();

    int size();

    boolean add(K key, V value);

    boolean add(K key, Supplier<?> supplier);

    boolean replace(K key, V value);

    boolean replace(K key, Supplier<?> supplier);

    boolean merge(Storage<K, V> storage, boolean doOverride);

    boolean remove(K key);

    void clear();

}

package wcyoung.storage.instance;

import java.util.function.Supplier;

public interface Storage<K, V> {

    boolean has(K key);

    <T> T get(K key);

    int size();

    boolean add(K key, V value);

    <T> boolean add(K key, Supplier<T> supplier);

    boolean replace(K key, V value);

    boolean remove(K key);

    void clear();

}

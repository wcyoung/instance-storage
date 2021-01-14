package wcyoung.storage.instance;

import java.util.function.Supplier;

public interface Storage<K, V> {

    boolean has(K key);

    <T> T get(K key);

    int size();

    boolean add(K key, V value);

    boolean add(K key, Supplier<?> supplier);

    boolean replace(K key, V value);

    boolean replace(K key, Supplier<?> supplier);

    boolean remove(K key);

    void clear();

}

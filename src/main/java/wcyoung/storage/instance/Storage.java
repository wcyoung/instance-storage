package wcyoung.storage.instance;

public interface Storage<K, V> {

    boolean has(K key);

    <T> T get(K key);

    int size();

    boolean add(K key, V value);

    boolean replace(K key, V value);

    boolean remove(K key);

    void clear();

}

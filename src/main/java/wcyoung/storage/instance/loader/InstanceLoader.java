package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.Storage;

public interface InstanceLoader<K, V> {

    boolean load(Storage<K, V> storage);

}

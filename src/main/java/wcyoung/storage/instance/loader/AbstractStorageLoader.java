package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.Storage;

public abstract class AbstractStorageLoader<K, V> implements StorageLoader {

    protected Storage<K, V> storage;

    public AbstractStorageLoader(Storage<K, V> storage) {
        this.storage = storage;
    }

    public Storage<K, V> getStorage() {
        return storage;
    }

    public Storage<K, V> loadAndGet() {
        return load() ? storage : null;
    }

}

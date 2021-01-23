package wcyoung.storage.instance.loader;

import wcyoung.storage.instance.Storage;

public abstract class AbstractInstanceLoader<K, V> implements InstanceLoader {

    protected Storage<K, V> storage;

    public AbstractInstanceLoader(Storage<K, V> storage) {
        this.storage = storage;
    }

    public Storage<K, V> getStorage() {
        return storage;
    }

    public Storage<K, V> loadAndGet() {
        return load() ? storage : null;
    }

}

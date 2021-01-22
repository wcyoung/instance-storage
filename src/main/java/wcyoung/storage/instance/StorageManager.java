package wcyoung.storage.instance;

import wcyoung.storage.instance.loader.InstanceLoader;

public class StorageManager {

    private static StorageManager INSTANCE;

    private final ClassStorage storage;

    private StorageManager(ClassStorage storage) {
        this.storage = storage;
    }

    public static StorageManager getInstance() {
        if (INSTANCE == null) {
            throw new StorageInitializeException("StorageManager is not initialized yet.");
        }

        return INSTANCE;
    }

    public ClassStorage get() {
        return storage;
    }

    public static class StorageInitializer {

        private ClassStorage storage;
        private InstanceLoader loader;

        public StorageInitializer(ClassStorage storage, InstanceLoader loader) {
            this.storage = storage;
            this.loader = loader;
        }

        public StorageManager initialize() {
            boolean isLoaded = loader.load(storage);
            if (!isLoaded) {
                throw new StorageInitializeException("Loading failed while storage initialization.");
            }

            INSTANCE = new StorageManager(storage);

            return INSTANCE;
        }

    }

}

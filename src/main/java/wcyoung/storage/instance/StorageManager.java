package wcyoung.storage.instance;

import wcyoung.storage.instance.loader.InstanceLoader;

public class StorageManager {

    private static StorageManager instance;

    private final ClassStorage storage;

    private StorageManager(ClassStorage storage) {
        this.storage = storage;
    }

    public static StorageManager getInstance() {
        if (instance == null) {
            throw new StorageInitializeException("StorageManager is not initialized yet.");
        }

        return instance;
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
            if (instance != null) {
                throw new StorageInitializeException("StorageManager has already been initialized.");
            }

            boolean isLoaded = loader.load(storage);
            if (!isLoaded) {
                throw new StorageInitializeException("Loading failed while storage initialization.");
            }

            instance = new StorageManager(storage);

            return instance;
        }

        public boolean isInitialized() {
            return instance != null;
        }

    }

}

package wcyoung.storage.instance;

import wcyoung.storage.instance.loader.AbstractStorageLoader;

public class StorageManager {

    private static StorageManager instance;

    private final ClassStorage STORAGE;

    private StorageManager(ClassStorage storage) {
        this.STORAGE = storage;
    }

    public static StorageManager getInstance() {
        if (!isInitialized()) {
            throw new StorageInitializeException("StorageManager is not initialized yet.");
        }

        return instance;
    }

    public static boolean isInitialized() {
        return instance != null;
    }

    public ClassStorage get() {
        return STORAGE;
    }

    public static class StorageInitializer {

        private AbstractStorageLoader<Class<?>, Object> loader;

        public StorageInitializer(AbstractStorageLoader<Class<?>, Object> loader) {
            this.loader = loader;
        }

        public StorageManager initialize() {
            if (loader == null) {
                throw new NullPointerException("loader is null");
            }

            if (isInitialized()) {
                throw new StorageInitializeException("StorageManager has already been initialized.");
            }

            boolean isLoaded = loader.load();
            if (!isLoaded) {
                throw new StorageInitializeException("Loading failed while storage initialization.");
            }

            instance = new StorageManager((ClassStorage) loader.getStorage());

            return instance;
        }

    }

}

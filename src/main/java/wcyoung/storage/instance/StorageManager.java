package wcyoung.storage.instance;

public class StorageManager {

    private static StorageManager INSTANCE = new StorageManager();

    private final ClassStorage storage;

    private StorageManager() {
        storage = new ClassStorage();
    }

    public static StorageManager getInstance() {
        return INSTANCE;
    }

    public ClassStorage get() {
        return storage;
    }

}

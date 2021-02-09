package wcyoung.storage.instance;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import wcyoung.storage.instance.classes.ClassA;
import wcyoung.storage.instance.loader.AbstractStorageLoader;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StorageManagerTest {

    /*
     * 1. before initializing
     */

    @Order(10)
    @Test
    void isNotInitialized() {
        assertFalse(StorageManager.isInitialized());
    }

    @Order(20)
    @Test
    void getInstanceFail() {
        assertThrows(StorageInitializeException.class, StorageManager::getInstance);
    }

    @Order(30)
    @Test
    void initializeFailBecauseLoaderIsNull() {
        StorageManager.StorageInitializer initializer = new StorageManager.StorageInitializer(null);
        assertThrows(NullPointerException.class, initializer::initialize);
    }

    @Order(40)
    @Test
    void initializeFailBecauseLoadFail() {
        ClassStorage classStorage = new ClassStorage();
        AbstractStorageLoader<Class<?>, Object> loader = new AbstractStorageLoader<Class<?>, Object>(classStorage) {
            @Override
            public boolean load() {
                return false;
            }
        };

        StorageManager.StorageInitializer initializer = new StorageManager.StorageInitializer(loader);
        assertThrows(StorageInitializeException.class, initializer::initialize);
    }

    /*
     * 2. initializing
     */

    @Order(110)
    @Test
    void initialize() {
        ClassStorage classStorage = new ClassStorage();
        AbstractStorageLoader<Class<?>, Object> loader = new AbstractStorageLoader<Class<?>, Object>(classStorage) {
            @Override
            public boolean load() {
                storage.add(ClassA.class, new ClassA());
                return true;
            }
        };

        StorageManager.StorageInitializer initializer = new StorageManager.StorageInitializer(loader);
        StorageManager storageManager = initializer.initialize();
        assertNotNull(storageManager);
        assertSame(storageManager, StorageManager.getInstance());
    }

    @Order(120)
    @Test
    void isInitialized() {
        assertTrue(StorageManager.isInitialized());
    }

    @Order(130)
    @Test
    void get() {
        StorageManager storageManager = StorageManager.getInstance();
        ClassStorage storage = storageManager.get();
        assertNotNull(storage);
        assertTrue(storage.has(ClassA.class));
    }

    /*
     * 3. after initializing
     */

    @Order(210)
    @Test
    void initializeFailBecauseAlreadyBeenInitialized() {
        ClassStorage classStorage = new ClassStorage();
        AbstractStorageLoader<Class<?>, Object> loader = new AbstractStorageLoader<Class<?>, Object>(classStorage) {
            @Override
            public boolean load() {
                return true;
            }
        };

        StorageManager.StorageInitializer initializer = new StorageManager.StorageInitializer(loader);
        assertThrows(StorageInitializeException.class, initializer::initialize);
    }

}
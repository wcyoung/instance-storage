package wcyoung.storage.instance.loader;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.ClassStorage;
import wcyoung.storage.instance.classes.ClassA;
import wcyoung.storage.instance.scanner.ClassScanner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MappedClassesLoaderTest {

    @Test
    void load() {
        ClassStorage storage = new ClassStorage();
        Map<Class<?>, Class<?>> classes = new HashMap<>();
        classes.put(ClassA.class, ClassA.class);

        MappedClassesLoader loader = new MappedClassesLoader(storage, classes);
        boolean isLoaded = loader.load();
        assertTrue(isLoaded);
        assertTrue(storage.has(ClassA.class));
    }

    @Test
    void loadWithScanner() {
        ClassStorage storage = new ClassStorage();
        ClassScanner<Map<Class<?>, Class<?>>> scanner = new ClassScanner<Map<Class<?>, Class<?>>>() {
            @Override
            public Map<Class<?>, Class<?>> scan() {
                Map<Class<?>, Class<?>> classes = new HashMap<>();
                classes.put(ClassA.class, ClassA.class);
                return classes;
            }
        };

        MappedClassesLoader loader = new MappedClassesLoader(storage, scanner);
        boolean isLoaded = loader.load();
        assertTrue(isLoaded);
        assertTrue(storage.has(ClassA.class));
    }

    @Test
    void loadFailBecauseStorageNull() {
        Map<Class<?>, Class<?>> classes = new HashMap<>();
        classes.put(ClassA.class, ClassA.class);

        MappedClassesLoader loader = new MappedClassesLoader(null, classes);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseClassesNull() {
        ClassStorage storage = new ClassStorage();

        MappedClassesLoader loader = new MappedClassesLoader(storage, (Map<Class<?>, Class<?>>) null);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

    @Test
    void loadFailBecauseScannerNull() {
        ClassStorage storage = new ClassStorage();

        MappedClassesLoader loader = new MappedClassesLoader(storage, (ClassScanner<Map<Class<?>, Class<?>>>) null);
        boolean isLoaded = loader.load();
        assertFalse(isLoaded);
    }

}
